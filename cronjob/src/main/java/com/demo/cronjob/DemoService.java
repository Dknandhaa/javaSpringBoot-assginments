package com.demo.cronjob;

import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class DemoService {

    public void dummyMethod() {
        System.out.println("Scheduled task executed at: " + new Date());
    }
}