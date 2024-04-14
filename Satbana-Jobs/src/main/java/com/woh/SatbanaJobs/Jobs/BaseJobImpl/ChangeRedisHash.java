package com.woh.SatbanaJobs.Jobs.BaseJobImpl;

import com.woh.SatbanaJobs.Jobs.BaseJob.IBaseJob;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties("change-redis-hash")
@Component
public class ChangeRedisHash implements IBaseJob {
    private String id;
    private String cron;

    @Override
    public void execute() {
        System.out.println("ChangeRedisHash");
    }
}
