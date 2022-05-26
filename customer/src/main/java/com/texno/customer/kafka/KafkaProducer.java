package com.texno.customer.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void send(String topic, Object value) {
        kafkaTemplate
                .send(topic, value)
                .addCallback(new ListenableFutureCallback<>() {
                    @Override
                    public void onSuccess(SendResult<String, Object> result) {
                        log.info("Send to Kafka: Topic: {}; Offset: {}; Value: {}",
                                 result.getProducerRecord().topic(),
                                 result.getRecordMetadata().offset(),
                                 result.getProducerRecord().value());
                    }

                    @Override
                    public void onFailure(Throwable ex) {

                    }
                });
    }
}
