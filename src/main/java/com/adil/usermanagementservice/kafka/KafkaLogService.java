package com.adil.usermanagementservice.kafka;

import com.adil.usermanagementservice.domain.model.dto.LogMessageDto;
import com.adil.usermanagementservice.domain.model.enums.LogLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class KafkaLogService {

    private final KafkaTemplate<String, LogMessageDto> kafkaTemplate;

    public void log(LogLevel level, String message) {
        String TOPIC = "logs-topic";
        var log = new LogMessageDto(
                level,
                message,
                Instant.now()
        );
        kafkaTemplate.send(TOPIC, log);
    }
}
