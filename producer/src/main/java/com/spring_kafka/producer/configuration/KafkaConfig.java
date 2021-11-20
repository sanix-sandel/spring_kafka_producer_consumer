package com.spring_kafka.producer.configuration;

import com.spring_kafka.producer.entity.User;
import com.spring_kafka.producer.services.ProducerServiceImpl;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Bean
    public ProducerFactory<Integer, String>producerFactory(){
        DefaultKafkaProducerFactory producerFactory=new DefaultKafkaProducerFactory(producerFactoryProperties());
        return producerFactory;
    }


    @Bean
    public Map<String, Object>producerFactoryProperties(){
        Map<String, Object> config=new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

       return config;

    }

    @Bean
    public KafkaTemplate<Integer, String>kafkaTemplate(){
        KafkaTemplate<Integer, String>kafkaTemplate=new KafkaTemplate<>(producerFactory());
        return kafkaTemplate;
    }

    @Bean
    public ProducerServiceImpl producerService(){
        return new ProducerServiceImpl(kafkaTemplate());
    }

}
