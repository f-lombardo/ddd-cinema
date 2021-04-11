package cinema.command;

import cinema.domain.Customer;
import cinema.domain.Seat;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
public class ReserveSeats implements Command {
    Customer customer;
    UUID plannedScreeningId;
    List<Seat> seats;
}
