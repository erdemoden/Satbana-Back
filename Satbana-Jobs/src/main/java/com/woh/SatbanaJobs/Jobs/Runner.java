package com.woh.SatbanaJobs.Jobs;

import com.woh.SatbanaJobs.Jobs.BaseJob.IBaseJob;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.jobrunr.scheduling.JobScheduler;

import java.util.List;

@RequiredArgsConstructor
public class Runner {

    private final JobScheduler jobScheduler;
    private final List<IBaseJob> jobs;
    @PostConstruct
    private void init(){
        jobs.forEach(job->{
            jobScheduler.scheduleRecurrently(job.getId(),job.getCron(),()->{
                job.execute();
            });
        });
    }
}
