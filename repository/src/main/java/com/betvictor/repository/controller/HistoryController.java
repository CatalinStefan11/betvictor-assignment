package com.betvictor.repository.controller;

import com.betvictor.repository.model.History;
import com.betvictor.repository.service.HistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class HistoryController {

  private HistoryService historyService;

  public HistoryController(HistoryService historyService) {
    this.historyService = historyService;
  }

  @GetMapping(value = "/betvictor/history")
  public List<History> getHistory() {
    return historyService.getHistory();
  }

}