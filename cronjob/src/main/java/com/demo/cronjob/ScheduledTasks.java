package com.demo.cronjob;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    @Autowired
    private DemoService demoService;

    @Scheduled(cron = "0 * * * * *")
    public void execute() {
        demoService.dummyMethod();
    }
}