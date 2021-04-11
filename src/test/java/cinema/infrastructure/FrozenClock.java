package cinema.infrastructure;

import cinema.domain.Now;

import java.time.LocalDateTime;

public class FrozenClock implements LocalClock {

    private final Now now;

    public FrozenClock(LocalDateTime frozenNow) {
        this.now = new Now(frozenNow);
    }

    @Override
    public Now now() {
        return now;
    }

}
