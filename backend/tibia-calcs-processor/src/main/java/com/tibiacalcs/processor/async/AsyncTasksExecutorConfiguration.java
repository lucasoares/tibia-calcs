package com.tibiacalcs.processor.async;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Log4j2
@EnableAsync
@Configuration
public class AsyncTasksExecutorConfiguration {

  private ThreadPoolTaskExecutor executor;

  @Bean
  public Executor taskExecutor(@Value("${tibiacalcs.async-tasks.threads:8}") int threads) {
    this.executor = new ThreadPoolTaskExecutor();
    this.executor.setCorePoolSize(threads);
    this.executor.setMaxPoolSize(threads);
    this.executor.setThreadNamePrefix("Async-Task-");
    this.executor.setDaemon(false);
    this.executor.setWaitForTasksToCompleteOnShutdown(true);
    this.executor.initialize();

    return this.executor;
  }

  @Scheduled(initialDelay = 20 * 1000, fixedRate = 10 * 1000)
  public void showExecutorStats() {
    if (this.executor == null) {
      return;
    }

    ThreadPoolExecutor threadPoolExecutor = this.executor.getThreadPoolExecutor();

    log.debug("Executor Statistics: queue({}), active({}), scheduled({}), completed({}).",
        threadPoolExecutor.getQueue().size(), this.executor.getActiveCount(),
        threadPoolExecutor.getCompletedTaskCount(), threadPoolExecutor.getTaskCount());
  }
}
