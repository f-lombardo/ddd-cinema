package cinema.events;

import cinema.domain.Customer;
import cinema.domain.ExpirationTime;
import cinema.domain.Seat;
import cinema.events.Event;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
public class SeatsReserved extends Event {
    Customer customer;
    List<Seat> seats;
    UUID screeningTimeId;
    ExpirationTime expirationTime;
}