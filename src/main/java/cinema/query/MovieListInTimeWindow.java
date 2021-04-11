package cinema.query;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class MovieListInTimeWindow implements Query {
    LocalDateTime startFrom;        // TODO la gestione del tempo va migliorata, il value object può essre LocalDateTime
    LocalDateTime startTo;

    public static MovieListInTimeWindow MovieListInTimeWindow(LocalDateTime startFrom, LocalDateTime startTo) {
        return new MovieListInTimeWindow(startFrom, startTo);
    }
}
