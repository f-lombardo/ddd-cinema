package testframework;

import cinema.command.Command;
import cinema.events.Event;
import cinema.infrastructure.*;
import cinema.query.Query;
import cinema.readmodel.QueryResult;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BDDBaseTest {

    private InMemoryTestPlannedScreeningRepository inMemoryTestPlannedScreeningRepository;
    private LocalClock localClock;
    private QueryResult currentQueryResult;

    protected void Given(Event... events) {
        this.inMemoryTestPlannedScreeningRepository = new InMemoryTestPlannedScreeningRepository(new ArrayList(Arrays.asList(events)));
    }

    protected void When(LocalDateTime frozenTime, Command command) {
        localClock = new FrozenClock(frozenTime);
        CommandHandler commandHandler = new CommandHandler(inMemoryTestPlannedScreeningRepository, localClock);
        commandHandler.handle(command);
    }

    protected void Then(Event... events) {
        assertThat(inMemoryTestPlannedScreeningRepository.getTestPublishedEvents(), is(Arrays.asList(events)));
    }

    protected void Query(Query query) {
        // TODO: come il query handler fa il rolling di tutti gli eventi?
        QueryHandler queryHandler = new QueryHandler(inMemoryTestPlannedScreeningRepository.loadFullHistory(), inMemoryTestPlannedScreeningRepository);
        currentQueryResult = queryHandler.handle(query);
    }

    protected void Then(QueryResult expectedQueryResult) {
        assertThat(this.currentQueryResult, is(expectedQueryResult));
    }

}
