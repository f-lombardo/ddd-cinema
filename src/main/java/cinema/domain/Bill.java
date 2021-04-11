package cinema.domain;

import lombok.Value;

@Value
public class Bill {
    Money amount;
    Reservation reservation;
}
