package cinema.integration;

import cinema.command.ReserveSeats;
import cinema.events.PlannedScreeingAllocated;
import cinema.events.PlannedScreeningCreated;
import cinema.query.AskForReservedSeats;
import cinema.readmodel.ReservedSeats;
import org.junit.jupiter.api.Test;
import testframework.BDDBaseTest;

import java.util.Arrays;

import static testframework.TestScenario.*;

public class IntegrationsTestWithCommandsAndQuery extends BDDBaseTest {


    @Test
    public void theReservationIsPaid() {

        Given(
                new PlannedScreeningCreated(Planned_Screening_ID1, Thor_Ragnarok, Scheduling_At_15_Of_May_2021_At_4_00_PM),
                new PlannedScreeingAllocated(Red_Room)
        );

        When(
                NowIs_01_Of_May_2021_At_4_30_PM,
                new ReserveSeats(Jane_Brown, Planned_Screening_ID1, Arrays.asList(Seat_A1))
        );

        Query(
                new AskForReservedSeats(Jane_Brown)
        );

        Then(
                new ReservedSeats.ReservedSeatsResults(Planned_Screening_ID1, Arrays.asList(Seat_A1))
        );

    }

}
