package com.woh.SatbanaJobs.Jobs.BaseJob;

import org.springframework.stereotype.Component;

@Component
public interface IBaseJob {

    void execute();

    String getId();

    String getCron();
}
