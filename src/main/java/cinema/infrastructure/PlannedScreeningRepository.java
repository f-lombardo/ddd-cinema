package cinema.infrastructure;

import cinema.events.Event;

import java.util.List;
import java.util.UUID;

public interface PlannedScreeningRepository {

    List<Event> loadPlannedScreeningEvents(UUID id);

    void persistPlannedScreeningEvents(List<Event> eventList);

}
