package com.stc.exporter.config.utils;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class BrowserOpener implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        try {
            Runtime.getRuntime().exec("open http://localhost:8081/swagger-ui/index.html");
//            Runtime.getRuntime().exec("open http://localhost:8081/");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
