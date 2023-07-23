package com.betvictor.repository.service;

import com.betvictor.repository.model.History;
import com.betvictor.repository.repository.HistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class HistoryService {

  private HistoryRepository historyRepository;

  public HistoryService(HistoryRepository historyRepository) {
    this.historyRepository = historyRepository;
  }

  public void addHistory(History history) {
    history.setCreatedAt(LocalDateTime.now());
    historyRepository.save(history);
    
  }

  public List<History> getHistory() {
    Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
    return historyRepository.findAll(pageable).getContent();
  }
}