package com.spring_kafka.consumer.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.SerializationException;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class KafkaErrHandler implements ErrorHandler {

    private void seekSerializeException(Exception e, Consumer<?, ?> consumer) {
        String p = ".*partition (.*) at offset ([0-9]*).*";
        Pattern r = Pattern.compile(p);

        Matcher m = r.matcher(e.getMessage());

        if (m.find()) {
            int idx = m.group(1).lastIndexOf("-");
            String topics = m.group(1).substring(0, idx);
            int partition = Integer.parseInt(m.group(1).substring(idx));
            int offset = Integer.parseInt(m.group(2));

            TopicPartition topicPartition = new TopicPartition(topics, partition);

            consumer.seek(topicPartition, (offset + 1));

            log.info("Skipped message with offset {} from partition {}", offset, partition);
        }
    }

    public void handle(Exception e, ConsumerRecord<?, ?> record, Consumer<?, ?> consumer) {
        log.error("Error in process with Exception {} and the record is {}", e, record);

        if (e instanceof SerializationException)
            seekSerializeException(e, consumer);
    }

    public void handle(Exception e, List<ConsumerRecord<?, ?>> records, Consumer<?, ?> consumer,
                       MessageListenerContainer container) {
        log.error("Error in process with Exception {} and the records are {}", e, records);

        if (e instanceof SerializationException)
            seekSerializeException(e, consumer);

    }

    public void handle(Exception e, ConsumerRecord<?, ?> record) {
        log.error("Error in process with Exception {} and the record is {}", e, record);
    }

    @Override
    public void warning(SAXParseException exception) throws SAXException {

    }

    @Override
    public void error(SAXParseException exception) throws SAXException {

    }

    @Override
    public void fatalError(SAXParseException exception) throws SAXException {

    }
}
