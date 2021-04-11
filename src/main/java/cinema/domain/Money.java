package cinema.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Money {
    private final int amount;
    public Money(int amount) {
        if (amount < 0) {
            throw new RuntimeException("Invalid Money amount" + amount);
        } else {
            this.amount = amount;
        }
    }
}
