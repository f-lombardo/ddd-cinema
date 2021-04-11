package cinema.infrastructure;

import cinema.domain.Now;

public interface LocalClock {

    Now now();

}
