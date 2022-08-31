package ru.amin.kafkatester.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.amin.kafkatester.dto.MessageDto;

import java.time.LocalTime;

@Service
@Slf4j
public class MessageServiceImpl implements MessageService{

    private final KafkaTemplate<Long, MessageDto> kafkaMessageTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public MessageServiceImpl(KafkaTemplate<Long, MessageDto> kafkaMessageTemplate, ObjectMapper objectMapper) {
        this.kafkaMessageTemplate = kafkaMessageTemplate;
        this.objectMapper = objectMapper;
    }

    @Scheduled(initialDelay = 10000, fixedDelay = 5000)
    @Override
    public void produce() {
        MessageDto dto = createDto();
        log.info("<= sending {}", writeValueAsString(dto));
        kafkaMessageTemplate.send("test", dto);
    }

    private MessageDto createDto() {
        return new MessageDto("Message " + (LocalTime.now().toNanoOfDay() / 1000000));
    }

    private String writeValueAsString(MessageDto dto) {
        try {
            return objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Writing value to JSON failed: " + dto.toString());
        }
    }
}
