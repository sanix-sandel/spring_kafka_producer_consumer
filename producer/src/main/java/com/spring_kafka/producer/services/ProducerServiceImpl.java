package com.spring_kafka.producer.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring_kafka.producer.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;


@Service
@RequiredArgsConstructor
public class ProducerServiceImpl implements ProducerService{

    private final KafkaOperations<Integer, String> kafkaOperations;


    @Override
    public void sendMail(User user) {
        ListenableFuture<SendResult<Integer, String>>future=kafkaOperations.send("mails", convertToJson(user));
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {
                ex.printStackTrace();
            }

            @Override
            public void onSuccess(SendResult<Integer, String> result) {
                System.out.println("Result (success): " + result.getRecordMetadata());
            }
        });
    }

    private String convertToJson(User user){
        try{
            return new ObjectMapper().writeValueAsString(user);
        }catch (JsonProcessingException e){
            throw new IllegalArgumentException(e);
        }
    }
}
