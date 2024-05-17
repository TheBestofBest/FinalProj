package com.app.businessBridge.global.batch;

import com.app.businessBridge.domain.rebate.service.RebateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDate;

@Slf4j
@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private final RebateService rebateService;

    public BatchConfig(RebateService rebateService) {
        this.rebateService = rebateService;
    }

    @Bean
    public Job helloJob(JobRepository jobRepository, Step simpleStep1) {
        return new JobBuilder("helloJob", jobRepository)
                .start(simpleStep1)
                .build();
    }

    @Bean
    public Step helloStep1(JobRepository jobRepository, Tasklet helloStep1Tasklet1, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("helloStep1Tasklet1", jobRepository)
                .tasklet(helloStep1Tasklet1, platformTransactionManager)
                .build();
    }

    @Bean
    public Tasklet helloStep1Tasklet1() {
        return ((contribution, chunkContext) -> {
            LocalDate currentDate = LocalDate.now();
            String year = String.valueOf(currentDate.getYear());
            String month = String.valueOf(currentDate.getMonthValue());
            rebateService.createRebateAll(year, month);
            System.out.println("정산 자동 생성");
            return RepeatStatus.FINISHED;
        });
    }
}