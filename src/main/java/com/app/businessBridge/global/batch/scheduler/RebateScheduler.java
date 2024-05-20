package com.app.businessBridge.global.batch.scheduler;


import com.app.businessBridge.domain.rebate.service.RebateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
public class RebateScheduler {

    private final JobLauncher jobLauncher;

    private final Job job;

    public RebateScheduler(JobLauncher jobLauncher, Job job) {
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    // 초 분 시 일 월 년
    @Scheduled(cron = "0 1 * * * *")
    public void runJob() throws Exception {
        jobLauncher.run(job, new JobParameters());
    }

}
