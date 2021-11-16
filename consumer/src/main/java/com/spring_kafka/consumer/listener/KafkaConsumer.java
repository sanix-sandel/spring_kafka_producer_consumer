package com.spring_kafka.consumer.listener;

import com.spring_kafka.consumer.models.User;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "kafka_Example", groupId="group_id")
    public void consume(String message){
        System.out.println("Consumed message "+message);
    }

    @KafkaListener(topics = "kafka_Example_json", groupId = "group_json", containerFactory = "userKafkaListenerFactory")
    public void consumeJson(User user){
        System.out.println("Consumed JSON Message: "+user);
    }


}
