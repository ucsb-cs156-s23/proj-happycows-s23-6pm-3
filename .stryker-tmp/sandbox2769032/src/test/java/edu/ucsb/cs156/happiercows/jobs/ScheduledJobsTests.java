package edu.ucsb.cs156.happiercows.jobs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.ucsb.cs156.happiercows.entities.jobs.Job;
import edu.ucsb.cs156.happiercows.services.jobs.JobContext;
import edu.ucsb.cs156.happiercows.services.jobs.JobContextConsumer;
import edu.ucsb.cs156.happiercows.services.jobs.JobService;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;


// @Slf4j
// @ExtendWith(SpringExtension.class)
// @ContextConfiguration 

@RestClientTest(ScheduledJobs.class)
@AutoConfigureDataJpa
public class ScheduledJobsTests {

    private class MockJobContextConsumer implements JobContextConsumer {
        @Override
        public void accept(JobContext jobContext) {}
    }

    @MockBean
    UpdateCowHealthJobFactory updateCowHealthJobFactory;

    @MockBean
    MilkTheCowsJobFactory milkTheCowsJobFactory;

    @Autowired
    private ScheduledJobs scheduledJobs;

    @MockBean
    private JobService jobService;

    @Test
    void test_runUpdateCowHealthJobBasedOnCron() throws Exception {

        // Arrange

        Job job = Job.builder().build();
        MockJobContextConsumer mockJob = new MockJobContextConsumer();

       when(updateCowHealthJobFactory.create()).thenReturn(mockJob);
       when(jobService.runAsJob(any())).thenReturn(job);

        // Act

        scheduledJobs.runUpdateCowHealthJobBasedOnCron();

        // Assert

        verify(jobService, times(1)).runAsJob(mockJob);
        verify(updateCowHealthJobFactory, times(1)).create();

    }

    @Test
    void test_runMilkTheCowsJobBasedOnCron() throws Exception {

        // Arrange

        Job job = Job.builder().build();
        MockJobContextConsumer mockJob = new MockJobContextConsumer();

       when(milkTheCowsJobFactory.create()).thenReturn(mockJob);
       when(jobService.runAsJob(any())).thenReturn(job);

        // Act

        scheduledJobs.runMilkTheCowsJobBasedOnCron();

        // Assert

        verify(jobService, times(1)).runAsJob(mockJob);
        verify(milkTheCowsJobFactory, times(1)).create();

    }

  
}