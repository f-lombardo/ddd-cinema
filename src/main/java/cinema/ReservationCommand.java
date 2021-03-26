package cinema;

import cinema.domain.Customer;
import cinema.domain.ScreeningTime;
import cinema.domain.Seat;
import lombok.Value;

import java.util.List;

@Value
public class ReservationCommand {

    Customer customer;
    ScreeningTime screeningTime;        // TODO this is an entity coming from the UI
    List<Seat> seats;

    public ReservationCommand(Customer customer, ScreeningTime screeningTime, List<Seat> seats) {
        this.customer = customer;
        this.screeningTime = screeningTime;
        this.seats = seats;
    }

    public Customer getCustomer() {
        return customer;
    }

    public ScreeningTime getScreeningTime() {
        return screeningTime;
    }

    public List<Seat> getSeats() {
        return seats;
    }
}
