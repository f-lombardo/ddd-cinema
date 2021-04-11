package cinema.infrastructure;

import cinema.events.Event;
import cinema.query.AskForReservedSeats;
import cinema.query.MovieListInTimeWindow;
import cinema.query.Query;
import cinema.readmodel.MovieList;
import cinema.readmodel.QueryResult;
import cinema.readmodel.ReservedSeats;

import java.util.List;

public class QueryHandler {

    private final PlannedScreeningRepository plannedScreeningRepository;
    private final List<Event> eventStore;

    // TODO capire come sottoscrivere le projection
    public QueryHandler(List<Event> eventStore, PlannedScreeningRepository plannedScreeningRepository) {
        this.eventStore = eventStore;
        this.plannedScreeningRepository = plannedScreeningRepository;
    }

    public QueryResult handle(Query query) {
        if (query instanceof MovieListInTimeWindow) {
            MovieList movieList = new MovieList(eventStore);
            return movieList.movieListInTimeWindow((MovieListInTimeWindow)query);
        }
        if (query instanceof AskForReservedSeats) {
            ReservedSeats reservedSeats = new ReservedSeats(eventStore);
            return reservedSeats.askForReservedSeats((AskForReservedSeats)query);
        }
        return null;
    }

}
