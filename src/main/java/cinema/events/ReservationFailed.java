package cinema.events;

import cinema.domain.Customer;
import cinema.domain.RefusedReservationReasons;
import cinema.domain.Seat;
import lombok.Value;

import java.util.List;

@Value
public class ReservationFailed implements Event {
    Customer customer;
    List<Seat> seats;
    RefusedReservationReasons reason;
}
