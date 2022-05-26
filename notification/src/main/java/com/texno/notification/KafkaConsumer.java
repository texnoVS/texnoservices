package com.texno.notification;

import com.texno.clients.notification.NotificationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {
    private final NotificationService notificationService;

    @KafkaListener(topics = "#{'${kafka.topics.notification}'}", groupId = "#{'${spring.kafka.consumer.group-id}'}")
    public void listenNotificationEvent(ConsumerRecord<String, NotificationRequest> record) {
        log.info("Get Kafka message. Topic: {}; Offset: {}; Value: {}",
                 record.topic(),
                 record.offset(),
                 record.value());
        notificationService.send(record.value());
    }
}
