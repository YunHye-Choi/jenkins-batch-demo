package com.tmax.jenkinsbatchdemo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.builder.TaskletStepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SimpleJobConfiguration {
    private final static String jobName = "simpleJob";
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    @Qualifier(jobName)
    public Job simpleJob() {
        return new JobBuilder(jobName, jobRepository)
                .start(simpleStep(null))
                .build();
    }

    @Bean
    @JobScope
    public Step simpleStep(@Value("#{jobParameters[requestDate]}") String requestDate) {
        log.info(" ########## requestDate: {}", requestDate);
        Map<String, Object> params = new HashMap<>();
        return new StepBuilder(jobName + "Step", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    log.info(" >>>>> SimpleStep has executed at {}", LocalDateTime.now());
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }
}