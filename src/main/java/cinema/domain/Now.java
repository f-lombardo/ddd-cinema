package cinema.domain;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class Now {  // TODO: is a valid ValueObject?
    LocalDateTime now;
}
