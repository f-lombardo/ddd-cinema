package cinema.events;

import cinema.domain.Room;
import lombok.Value;

@Value
public class PlannedScreeingAllocated implements Event {
    Room room;
}
