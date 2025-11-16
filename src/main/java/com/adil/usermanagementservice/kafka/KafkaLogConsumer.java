package com.adil.usermanagementservice.kafka;

import com.adil.usermanagementservice.domain.model.dto.LogMessageDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;

@Component
public class KafkaLogConsumer {

    private static final String FILE_NAME = "application-kafka.log";

    @KafkaListener(topics = "logs-topic", groupId = "log-group")
    public void handleLog(LogMessageDto log) {
        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {

            String time = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .format(log.timestamp());

            writer.write(
                    "[" + time + "] " +
                    "[" + log.level() + "] " +
                    log.message() + "\n"
            );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


