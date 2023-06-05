package edu.ucsb.cs156.happiercows.jobs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import edu.ucsb.cs156.happiercows.entities.Commons;
import edu.ucsb.cs156.happiercows.entities.User;
import edu.ucsb.cs156.happiercows.entities.UserCommons;
import edu.ucsb.cs156.happiercows.entities.jobs.Job;
import edu.ucsb.cs156.happiercows.repositories.CommonsRepository;
import edu.ucsb.cs156.happiercows.repositories.ProfitRepository;
import edu.ucsb.cs156.happiercows.repositories.UserCommonsRepository;
import edu.ucsb.cs156.happiercows.repositories.UserRepository;
import edu.ucsb.cs156.happiercows.services.jobs.JobContext;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
public class MilkTheCowsJobTests {
    @Mock
    CommonsRepository commonsRepository;

    @Mock
    UserCommonsRepository userCommonsRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    ProfitRepository profitRepository;

    private User user = User
            .builder()
            .id(1L)
            .fullName("Chris Gaucho")
            .email("cgaucho@example.org")
            .build();

    @Test
    void test_log_output_no_commons() throws Exception {

        // Arrange

        Job jobStarted = Job.builder().build();
        JobContext ctx = new JobContext(null, jobStarted);

        // Act
        MilkTheCowsJob milkTheCowsJob = new MilkTheCowsJob(commonsRepository, userCommonsRepository,
                userRepository, profitRepository);

        milkTheCowsJob.accept(ctx);

        // Assert

        String expected = """
                Starting to milk the cows
                Cows have been milked!""";

        assertEquals(expected, jobStarted.getLog());
    }

    @Test
    void test_log_output_with_commons_and_user_commons() throws Exception {

        // Arrange
        Job jobStarted = Job.builder().build();
        JobContext ctx = new JobContext(null, jobStarted);

        UserCommons origUserCommons = UserCommons
                .builder()
                .id(1L)
                .userId(1L)
                .commonsId(1L)
                .totalWealth(300)
                .numOfCows(1)
                .cowHealth(10)
                .build();

        Commons testCommons = Commons
                .builder()
                .name("test commons")
                .cowPrice(10)
                .milkPrice(2)
                .startingBalance(300)
                .startingDate(2019-01-21T05:47:08.644)
                .lastDate(3000-01-21T05:47:08.644) //arbitrarily far into the future
                .carryingCapacity(100)
                .degradationRate(0.01)
                .build();

        Commons commonsTemp[] = { testCommons };
        UserCommons userCommonsTemp[] = { origUserCommons };
        when(commonsRepository.findAll()).thenReturn(Arrays.asList(commonsTemp));
        when(userCommonsRepository.findByCommonsId(testCommons.getId()))
                .thenReturn(Arrays.asList(userCommonsTemp));
        when(commonsRepository.getNumCows(testCommons.getId())).thenReturn(Optional.of(Integer.valueOf(1)));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        MilkTheCowsJob MilkTheCowsJob = new MilkTheCowsJob(commonsRepository, userCommonsRepository,
                userRepository, profitRepository);
        MilkTheCowsJob.accept(ctx);

        // Assert

        String expected = """
                Starting to milk the cows
                Milking cows for Commons: test commons, Milk Price: $2.00
                User: Chris Gaucho, numCows: 1, cowHealth: 10.0, totalWealth: $300.00
                Profit for user: Chris Gaucho is: $0.20, newWealth: $300.20
                Cows have been milked!""";

        assertEquals(expected, jobStarted.getLog());
    }

    @Test
    void test_milk_cows() throws Exception {

        // Arrange
        Job jobStarted = Job.builder().build();
        JobContext ctx = new JobContext(null, jobStarted);

        UserCommons origUserCommons = UserCommons
                .builder()
                .id(1L)
                .userId(1L)
                .commonsId(1L)
                .totalWealth(300)
                .numOfCows(1)
                .cowHealth(10)
                .build();

        Commons testCommons = Commons
                .builder()
                .name("test commons")
                .cowPrice(10)
                .milkPrice(2)
                .startingBalance(300)
                .startingDate(2019-01-21T05:47:08.644)
                .lastDate(3000-01-21T05:47:08.644) //arbitrarily far into the future
                .carryingCapacity(100)
                .degradationRate(0.01)
                .build();

        UserCommons updatedUserCommons = UserCommons
                .builder()
                .id(1L)
                .userId(1L)
                .commonsId(1L)
                .totalWealth(300.20)
                .numOfCows(1)
                .cowHealth(10)
                .build();

        Commons commonsTemp[] = { testCommons };
        UserCommons userCommonsTemp[] = { origUserCommons };
        when(commonsRepository.findAll()).thenReturn(Arrays.asList(commonsTemp));
        when(userCommonsRepository.findByCommonsId(testCommons.getId()))
                .thenReturn(Arrays.asList(userCommonsTemp));
        when(commonsRepository.getNumCows(testCommons.getId())).thenReturn(Optional.of(Integer.valueOf(1)));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userCommonsRepository.save(updatedUserCommons)).thenReturn(updatedUserCommons);


        // Act
        MilkTheCowsJob milkTheCowsJob = new MilkTheCowsJob(commonsRepository, userCommonsRepository,
                userRepository, profitRepository);
        milkTheCowsJob.milkCows(ctx, testCommons, origUserCommons);

        // Assert

        String expected = """
                User: Chris Gaucho, numCows: 1, cowHealth: 10.0, totalWealth: $300.00
                Profit for user: Chris Gaucho is: $0.20, newWealth: $300.20""";

        verify(userCommonsRepository).save(updatedUserCommons);
        assertEquals(expected, jobStarted.getLog());
    }


    @Test
    void test_throws_exception_when_getting_user_fails() throws Exception {

            // Arrange
            Job jobStarted = Job.builder().build();
            JobContext ctx = new JobContext(null, jobStarted);

            UserCommons origUserCommons = UserCommons
                            .builder()
                            .id(1L)
                            .userId(321L)
                            .commonsId(1L)
                            .totalWealth(300)
                            .numOfCows(1)
                            .cowHealth(10)
                            .build();

            Commons testCommons = Commons
                            .builder()
                            .id(117L)
                            .name("test commons")
                            .cowPrice(10)
                            .milkPrice(2)
                            .startingBalance(300)
                            .startingDate(2019-01-21T05:47:08.644)
                            .lastDate(3000-01-21T05:47:08.644) //arbitrarily far into the future
                            .carryingCapacity(100)
                            .degradationRate(0.01)
                            .build();

            Commons commonsTemp[] = { testCommons };
            UserCommons userCommonsTemp[] = { origUserCommons };
            when(commonsRepository.findAll()).thenReturn(Arrays.asList(commonsTemp));
            when(userCommonsRepository.findByCommonsId(testCommons.getId()))
                            .thenReturn(Arrays.asList(userCommonsTemp));
            when(commonsRepository.getNumCows(testCommons.getId())).thenReturn(Optional.of(10));
            when(userRepository.findById(321L)).thenReturn(Optional.empty());

            // Act
            MilkTheCowsJob MilkTheCowsJob = new MilkTheCowsJob(commonsRepository, userCommonsRepository,
                            userRepository, profitRepository);

            RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
                    // Code under test
                    MilkTheCowsJob.accept(ctx);
            });

            Assertions.assertEquals("Error calling userRepository.findById(321)", thrown.getMessage());

    }


    @Test
    void test_milkCows() throws Exception {

            // Arrange
            Job jobStarted = Job.builder().build();
            JobContext ctx = new JobContext(null, jobStarted);

            UserCommons origUserCommons = UserCommons
                            .builder()
                            .id(1L)
                            .userId(321L)
                            .commonsId(1L)
                            .totalWealth(300)
                            .numOfCows(1)
                            .cowHealth(10)
                            .build();

            Commons testCommons = Commons
                            .builder()
                            .id(117L)
                            .name("test commons")
                            .cowPrice(10)
                            .milkPrice(2)
                            .startingBalance(300)
                            .startingDate(2019-01-21T05:47:08.644)
                            .lastDate(3000-01-21T05:47:08.644) //arbitrarily far into the future
                            .carryingCapacity(100)
                            .degradationRate(0.01)
                            .build();

            Commons commonsTemp[] = { testCommons };
            UserCommons userCommonsTemp[] = { origUserCommons };
            when(commonsRepository.findAll()).thenReturn(Arrays.asList(commonsTemp));
            when(userCommonsRepository.findByCommonsId(testCommons.getId()))
                            .thenReturn(Arrays.asList(userCommonsTemp));
            when(commonsRepository.getNumCows(testCommons.getId())).thenReturn(Optional.of(10));
            when(userRepository.findById(321L)).thenReturn(Optional.empty());

            // Act
            MilkTheCowsJob MilkTheCowsJob = new MilkTheCowsJob(commonsRepository, userCommonsRepository,
                            userRepository, profitRepository);

            RuntimeException thrown = Assertions.assertThrows(RuntimeException.class, () -> {
                    // Code under test
                    MilkTheCowsJob.accept(ctx);
            });

            Assertions.assertEquals("Error calling userRepository.findById(321)", thrown.getMessage());

    }


    @Test
    void test_cannot_milk_cows_when_before_start_date() throws Exception {
            
        // Arrange
        Job jobStarted = Job.builder().build();
        JobContext ctx = new JobContext(null, jobStarted);

        UserCommons origUserCommons = UserCommons
                .builder()
                .id(1L)
                .userId(1L)
                .commonsId(1L)
                .totalWealth(300)
                .numOfCows(1)
                .cowHealth(10)
                .build();

        Commons testCommons = Commons
                .builder()
                .name("test commons")
                .cowPrice(10)
                .milkPrice(2)
                .startingBalance(300)
                .startingDate(3000-01-21T05:47:08.644) //arbitrarily far into the future
                .lastDate(3000-01-25T05:47:08.644)
                .carryingCapacity(100)
                .degradationRate(0.01)
                .build();

        Commons commonsTemp[] = { testCommons };
        UserCommons userCommonsTemp[] = { origUserCommons };
        when(commonsRepository.findAll()).thenReturn(Arrays.asList(commonsTemp));
        when(userCommonsRepository.findByCommonsId(testCommons.getId()))
                .thenReturn(Arrays.asList(userCommonsTemp));
        when(commonsRepository.getNumCows(testCommons.getId())).thenReturn(Optional.of(Integer.valueOf(1)));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        MilkTheCowsJob MilkTheCowsJob = new MilkTheCowsJob(commonsRepository, userCommonsRepository,
                userRepository, profitRepository);
        MilkTheCowsJob.accept(ctx);

        // Assert

        String expected = """
                Starting to milk the cows
                Milking cows for Commons: test commons, Milk Price: $2.00
                Commons test commons is not currently in progress, and cows were not milked.
                Cows have been milked!""";

        assertEquals(expected, jobStarted.getLog());
    }

    @Test
    void test_cannot_milk_cows_when_after_start_date() throws Exception {
            
        // Arrange
        Job jobStarted = Job.builder().build();
        JobContext ctx = new JobContext(null, jobStarted);

        UserCommons origUserCommons = UserCommons
                .builder()
                .id(1L)
                .userId(1L)
                .commonsId(1L)
                .totalWealth(300)
                .numOfCows(1)
                .cowHealth(10)
                .build();

        Commons testCommons = Commons
                .builder()
                .name("test commons")
                .cowPrice(10)
                .milkPrice(2)
                .startingBalance(300)
                .startingDate(2000-01-21T05:47:08.644)
                .lastDate(2000-01-25T05:44:08.644)
                .carryingCapacity(100)
                .degradationRate(0.01)
                .build();

        Commons commonsTemp[] = { testCommons };
        UserCommons userCommonsTemp[] = { origUserCommons };
        when(commonsRepository.findAll()).thenReturn(Arrays.asList(commonsTemp));
        when(userCommonsRepository.findByCommonsId(testCommons.getId()))
                .thenReturn(Arrays.asList(userCommonsTemp));
        when(commonsRepository.getNumCows(testCommons.getId())).thenReturn(Optional.of(Integer.valueOf(1)));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        MilkTheCowsJob MilkTheCowsJob = new MilkTheCowsJob(commonsRepository, userCommonsRepository,
                userRepository, profitRepository);
        MilkTheCowsJob.accept(ctx);

        // Assert

        String expected = """
                Starting to milk the cows
                Milking cows for Commons: test commons, Milk Price: $2.00
                Commons test commons is not currently in progress, and cows were not milked.
                Cows have been milked!""";

        assertEquals(expected, jobStarted.getLog());
    }

}