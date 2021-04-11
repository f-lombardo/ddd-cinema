package cinema.readmodel;

import cinema.domain.Customer;
import cinema.domain.Seat;
import cinema.events.Event;
import cinema.events.PlannedScreeningCreated;
import cinema.events.SeatsReserved;
import cinema.query.AskForReservedSeats;
import lombok.Value;

import java.util.*;

public class ReservedSeats {

    UUID uuid;
    Map<Customer, List<Seat>> reservations;

    public ReservedSeats(List<Event> eventStore) {
        reservations = new Hashtable<>();
        if (eventStore != null) {
            for (Event event : eventStore) {
                apply(event);
            }
        }
    }

    private void apply(Event event) {
        if (event instanceof PlannedScreeningCreated) {
            uuid = ((PlannedScreeningCreated)event).getId();
            return;
        }
        if (event instanceof SeatsReserved) {
            SeatsReserved seatsReservedEvent = (SeatsReserved)event;
            List currentSeats = reservations.getOrDefault(seatsReservedEvent.getCustomer(), new ArrayList<>());
            currentSeats.addAll(seatsReservedEvent.getSeats());
            reservations.put(seatsReservedEvent.getCustomer(), currentSeats);
            return;
        }
    }

    public QueryResult askForReservedSeats(AskForReservedSeats query) {
        return new ReservedSeatsResults(uuid, reservations.get(query.getCustomer()));
    }

    @Value
    public static class ReservedSeatsResults implements QueryResult {
        UUID Planned_Screening_ID;
        List<Seat> seats;
    }

}
