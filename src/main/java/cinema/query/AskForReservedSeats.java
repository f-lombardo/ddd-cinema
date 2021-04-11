package cinema.query;

import cinema.domain.Customer;
import lombok.Value;

@Value
public class AskForReservedSeats implements Query {
    Customer customer;
}
