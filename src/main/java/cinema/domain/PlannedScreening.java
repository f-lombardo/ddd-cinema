package cinema.domain;

import cinema.events.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Getter
public class PlannedScreening {

    UUID id;
    Movie movie;
    Room room;
    SchedulingTime schedulingTime;
    List<Seat> reservedSeats = new ArrayList<>();

    public PlannedScreening(List<Event> events) {
        if (events != null) {
            for (Event event : events) {
                apply(event);
            }
        }
    }

    private void apply(Event event) {
        if (event instanceof PlannedScreeningCreated) {
            this.id = ((PlannedScreeningCreated)event).getId();
            this.movie = ((PlannedScreeningCreated)event).getMovie();
            this.schedulingTime = ((PlannedScreeningCreated)event).getSchedulingTime();
            return;
        }
        if (event instanceof PlannedScreeingAllocated) {
            this.room = ((PlannedScreeingAllocated)event).getRoom();
            return;
        }
        if(event instanceof SeatsReserved) {
            this.reservedSeats.addAll(((SeatsReserved)event).getSeats());
            return;
        }
    }

    public List<Event> reserveSeats(Customer customer, List<Seat> seats, Now reservationTime) {
        if (!theReservationsAreStillOpen(reservationTime)) {
            return Arrays.asList(new ReservationFailed(customer, seats, RefusedReservationReasons.RESERVATION_TOO_CLOSE_TO_SCREENING_START));
        }
        if (!checkIfSeatsAreAvailable(seats)) {
            return Arrays.asList(new ReservationFailed(customer, seats, RefusedReservationReasons.SEATS_ALREADY_RESERVED));
        }
        return Arrays.asList(new SeatsReserved(customer, seats, id, calculateReservationExpirationTime(reservationTime)));
    }

    private boolean theReservationsAreStillOpen(Now reservationTime) {
        LocalDateTime lastReservationTime = schedulingTime.getLocalDateTime().minusMinutes(LAST_RESERVATION_WINDOW_MINUTES);
        return (reservationTime.getNow().isBefore(lastReservationTime));    // TODO: manage the time without look in the value objects fields
    }

    private boolean checkIfSeatsAreAvailable(List<Seat> seats) {
        for(Seat seat : seats) {
            if(reservedSeats.contains(seat)) {
                return false;
            }
        }
        return true;
    }

    private ExpirationTime calculateReservationExpirationTime(Now reservationTime) {
        return new ExpirationTime(reservationTime.getNow().plusMinutes(RESERVATION_EXPIRATION_MINUTES));
    }

    private static final int RESERVATION_EXPIRATION_MINUTES = 12;
    private static final int LAST_RESERVATION_WINDOW_MINUTES = 15;

}
