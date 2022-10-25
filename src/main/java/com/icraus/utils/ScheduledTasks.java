package com.icraus.utils;

import com.icraus.bussinesslogic.IDroneBusiness;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private final IDroneBusiness droneBusiness;

    public ScheduledTasks(IDroneBusiness droneBusiness) {
        this.droneBusiness = droneBusiness;
    }

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {

        log.info("The time is now {}", dateFormat.format(new Date()));
        log.info(String.join("\n", droneBusiness.getDronesInfo()));
    }
}
