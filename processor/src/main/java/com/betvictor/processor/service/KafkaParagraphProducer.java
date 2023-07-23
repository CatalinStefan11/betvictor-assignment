package com.betvictor.processor.service;

import com.betvictor.processor.model.ParagraphResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaParagraphProducer {

  @Value("${spring.kafka.producer.topic}")
  private String topic;

  private KafkaTemplate<String, ParagraphResponse> kafkaTemplate;

  public KafkaParagraphProducer(KafkaTemplate<String, ParagraphResponse> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void produceMessage(ParagraphResponse message) {
    kafkaTemplate.send(topic, message);
    log.info("Send message {} to {} topic", message, topic);
  }
}
