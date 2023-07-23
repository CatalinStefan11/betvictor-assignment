package com.betvictor.repository.kafka;

import com.betvictor.repository.model.History;
import com.betvictor.repository.service.HistoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WordsConsumer {
  
  private HistoryService historyService;

  public WordsConsumer(HistoryService historyService) {
    this.historyService = historyService;
  }

  @KafkaListener(
      topics = "words.processed", 
      groupId = "betvictor",
      containerFactory = "kafkaListenerContainerFactory"
  )
  public void consumeMessage(String message) {
    historyService.addHistory(processMessage(message));
  }

  private History processMessage(String message) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      History history = mapper.readValue(message, History.class);
      log.info("Received message {}", history.toString());
      return history;
    } catch (Exception ex) {
      log.error("Exception during deserializing message {}", message, ex);
      throw new RuntimeException(ex);
    }
  }
}
