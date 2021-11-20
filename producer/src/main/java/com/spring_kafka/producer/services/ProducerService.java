package com.spring_kafka.producer.services;

import com.spring_kafka.producer.entity.User;
import org.springframework.kafka.core.KafkaOperations;

public interface ProducerService {

    void sendMail(final User user);
}
