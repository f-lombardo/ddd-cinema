package cinema.readmodel;

import cinema.domain.Movie;
import cinema.events.Event;
import cinema.events.PlannedScreeningCreated;
import cinema.query.MovieListInTimeWindow;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class MovieList {

    private Map<LocalDateTime, Movie> movieplanning;

    public MovieList(List<Event> eventStore) {
        if (eventStore != null) {
            movieplanning = new Hashtable<>();
            for (Event event : eventStore) {
                apply(event);
            }
        }
    }

    public void onEvent(Event event) {  // TODO a che serve?
        apply(event);
    }

    private void apply(Event event) {
        if (event instanceof PlannedScreeningCreated) {
            movieplanning.put(
                    ((PlannedScreeningCreated)event).getSchedulingTime().getLocalDateTime(),
                    ((PlannedScreeningCreated)event).getMovie()
                    );
        }
    }

    public QueryResult movieListInTimeWindow(MovieListInTimeWindow query) {
        List<Movie> results = new ArrayList<>();
        for (LocalDateTime schedulingTime: movieplanning.keySet()) {
            if (schedulingTime.isAfter(query.getStartFrom()) && schedulingTime.isBefore(query.getStartTo())) {
                results.add(movieplanning.get(schedulingTime));
            }
        }
        return new MovieListResults(results);
    }

    @Value
    public static class MovieListResults implements QueryResult {
        List<Movie> movieList;
    }

}
