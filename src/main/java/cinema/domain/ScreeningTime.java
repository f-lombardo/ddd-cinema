package cinema.domain;

import cinema.*;
import lombok.Getter;

import java.util.*;

@Getter
public class ScreeningTime {

    UUID id;
    Movie movie;
    Room room;
    SchedulingTime schedulingTime;
    List<Seat> reservedSeats;

    public ScreeningTime(List<Event> events) {
        if (events != null) {
            reservedSeats = new ArrayList<>();
            for (Event event : events) {
                apply(event);
            }
        }
    }

    private void apply(Event event) {
        if (event instanceof ScreeningTimeCreated) {
            this.id = ((ScreeningTimeCreated)event).id;
            return;
        }
        if (event instanceof ScreeningTimeScheduled) {
            this.schedulingTime = ((ScreeningTimeScheduled)event).schedulingTime;
            this.movie = ((ScreeningTimeScheduled)event).movie;
            return;
        }
        if (event instanceof ScreeingTimeAllocated) {
            this.room = ((ScreeingTimeAllocated)event).room;
            return;
        }
        if(event instanceof SeatsReserved) {
            this.reservedSeats.addAll(((SeatsReserved)event).seats);
            return;
        }
    }

    public List<Event> reserveSeats(Customer customer, List<Seat> seats) {
        // reservedSeats.addAll(seats);
        // TODO what I'll do with the reservation object?
        // TODO the entity is included in the value object
        // Reservation reservation =  new Reservation(customer, seats, this, new ExpirationTime(new Date()));
        // TODO BL
        for(Seat seat : seats) {
            if(reservedSeats.contains(seat)) {
                return Arrays.asList(new FailedReservation("Already reserved", seats, customer));
            }
        }
        Event _event = new SeatsReserved(customer, seats, id, new ExpirationTime(new Date()));
        return Arrays.asList(_event);
    }

}
