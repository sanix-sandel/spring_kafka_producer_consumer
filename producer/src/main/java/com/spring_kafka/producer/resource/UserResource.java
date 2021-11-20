package com.spring_kafka.producer.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring_kafka.producer.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("kafka")
@RequiredArgsConstructor
public class UserResource {

    private final KafkaTemplate<String, User>kafkaTemplate;

    private static final String TOPIC="kafka_json";

    @PostMapping(value = "/publish/{name}", produces = {"application/json"})
    public String post(@PathVariable("name")String name){
        System.out.println(name);
        User user=new User();
        user.setName(name);
        user.setDept("Technology");
        user.setSalary(12000L);
        System.out.println(user);
        kafkaTemplate.send(TOPIC, user);
        return "Published successfully";
    }

    private String convertToJson(User user){
        try{
            return new ObjectMapper().writeValueAsString(user);
        }catch (JsonProcessingException e){
            throw new IllegalArgumentException(e);
        }
    }

}
