package com.spring_kafka.producer.resource;

import com.spring_kafka.producer.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("kafka")
@RequiredArgsConstructor
public class UserResource {

    private final KafkaTemplate<String, Object>kafkaTemplate;

    private static final String TOPIC="kafka_Example_json";

    @GetMapping("/publish/{name}")
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

}
