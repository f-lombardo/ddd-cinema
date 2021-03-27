package cinema;

import cinema.domain.*;
import cinema.events.*;
import cinema.repository.InMemoryTestEventPusher;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class IReserveSeatsForACustomer {

    private List<Event> history; // TODO create eventrepository interface also for the store part
    private InMemoryTestEventPusher eventPusher;

    private void Given(Event ... events) {
        this.history = Arrays.asList(events);
    }

    private void When(ReservationCommand reservationCommand) {
        eventPusher = new InMemoryTestEventPusher();
        CommandHandler commandHandler = new CommandHandler(history, eventPusher);
        commandHandler.handle(reservationCommand);
    }

    private void Then(Event ... events) {
        assertThat(eventPusher.getEvents(), is(Arrays.asList(events)));
    }

    @Test
    void TheReservationCompleteSuccessfully() {

        UUID uuid = UUID.randomUUID();
        Movie movie = new Movie("Start Trek");
        Customer customer = new Customer("James", "Kirk");
        Room room = new Room("Enterprise");
        List<Seat> seats = Arrays.asList(new Seat("A", 1), new Seat("A", 2));
        LocalDateTime frozenNow = LocalDateTime.now();
        SchedulingTime schedulingTime = new SchedulingTime(frozenNow);

        Given(
                new PlannedScreeningCreated(uuid),
                new PlannedScreeningScheduled(movie, schedulingTime),
                new PlannedScreeingAllocated(room)
        );

        ReservationCommand reservationCommand = new ReservationCommand(customer, uuid, seats);

        When(reservationCommand);

        Then(
                new SeatsReserved(customer, seats, uuid, new ExpirationTime(frozenNow))
        );

    }

    @Test
    void OneOfTheChosenSeatsIsNotAvailable() {

        UUID uuid = UUID.randomUUID();
        Movie movie = new Movie("Start Trek");
        Customer customer = new Customer("James", "Kirk");
        Room room = new Room("Enterprise");
        List<Seat> seats = Arrays.asList(new Seat("A", 1), new Seat("A", 2));
        LocalDateTime frozenNow = LocalDateTime.now();
        SchedulingTime schedulingTime = new SchedulingTime(frozenNow);

        Given(
                new PlannedScreeningCreated(uuid),
                new PlannedScreeningScheduled(movie, schedulingTime),
                new PlannedScreeingAllocated(room),
                new SeatsReserved(customer, seats, uuid, new ExpirationTime(frozenNow))
        );


        ReservationCommand reservationCommand = new ReservationCommand(customer, uuid, seats);

        When(reservationCommand);

        Then(
                new ReservationFailed(customer, seats, RefusedReservationReason.SEATS_ALREADY_RESERVED)
        );

    }

    void RequestIsTooCloseToTheMovieBeginning() {
        // TODO, max 15 minutes
    }

    void DueToCovid19TheNearestSeatsMustToBeFree() {

    }

    // TODO adult, student, child with varying prices

    // TODO 3D Movies and D-Box Seats cost extra. For students the price gets procentually redacted.

}
