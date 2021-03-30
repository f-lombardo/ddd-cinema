package cinema.readmodel;


import org.junit.jupiter.api.Test;
import testframework.BDDBaseTest;

import java.util.Arrays;

import static cinema.events.PlannedScreeningScheduled.PlannedScreeningScheduled;
import static cinema.query.MovieListInTimeWindow.MovieListInTimeWindow;
import static testframework.TestEnvironment.*;

public class MovieListReadModelTest extends BDDBaseTest {

    @Test
    public void IWantToListMoviesInASpecificDateTime_NarrowRange() {

        Given(
                PlannedScreeningScheduled(The_Wolf_of_Wall_Street, Scheduling_At_15_Of_May_2021_At_4_00_PM),
                PlannedScreeningScheduled(Thor_Ragnarok, Scheduling_At_15_Of_May_2021_At_5_00_PM),
                PlannedScreeningScheduled(Guardian_Of_The_Galaxy, Scheduling_At_15_Of_May_2021_At_6_00_PM)
        );

        Query(
                MovieListInTimeWindow(T_15_Of_May_2021_At_4_05_PM, T_15_Of_May_2021_At_5_58_PM)
        );

        Then(
                new MovieList.MovieListResults(Arrays.asList(Thor_Ragnarok))
        );

    }

    //@Test
    public void IWantToListMoviesInASpecificDateTime_FullRange() {

        Given(
                PlannedScreeningScheduled(The_Wolf_of_Wall_Street, Scheduling_At_15_Of_May_2021_At_4_00_PM),
                PlannedScreeningScheduled(Thor_Ragnarok, Scheduling_At_15_Of_May_2021_At_5_00_PM),
                PlannedScreeningScheduled(Guardian_Of_The_Galaxy, Scheduling_At_15_Of_May_2021_At_6_00_PM)
        );

        Query(
                MovieListInTimeWindow(T_15_Of_May_2021_At_3_30_PM, T_15_Of_May_2021_At_7_30_PM)
        );

        Then(
                new MovieList.MovieListResults(Arrays.asList(The_Wolf_of_Wall_Street, Thor_Ragnarok, Guardian_Of_The_Galaxy))
        );

    }

}