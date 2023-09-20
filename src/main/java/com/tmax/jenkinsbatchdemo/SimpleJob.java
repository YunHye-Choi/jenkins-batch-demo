package com.tmax.jenkinsbatchdemo;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.repository.JobRepository;

@Slf4j
@AllArgsConstructor
public class SimpleJob implements Job {
    private JobRepository jobRepository;
    @Override
    public String getName() {
        return "SampleJob";
    }

    @Override
    public void execute(JobExecution execution) {
        execution.setStatus(BatchStatus.COMPLETED);
        execution.setExitStatus(ExitStatus.COMPLETED);
        jobRepository.update(execution);

        log.info("job executing: {}", this.getName());
    }
}
