package com.betvictor.repository.controller;

import com.betvictor.repository.model.History;
import com.betvictor.repository.service.HistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class HistoryControllerTest {

  @Mock
  private HistoryService historyService;

  @InjectMocks
  private HistoryController underTest;
  private MockMvc mockMvc;

  @BeforeEach
  public void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(underTest).build();
  }

  @Test
  public void testHistoryController() throws Exception {
    List<History> histories = new ArrayList<>();
    histories.add(createHistory());
    when(historyService.getHistory()).thenReturn(histories);

    mockMvc.perform(get("/betvictor/history")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].freq_word", is(histories.get(0).getFrequentWord())))
        .andExpect(jsonPath("$[0].avg_paragraph_size", is(histories.get(0).getAverageParagraphSize())))
        .andExpect(jsonPath("$[0].avg_paragraph_processing_time", is(histories.get(0).getAverageParagraphProcessingTime())))
        .andExpect(jsonPath("$[0].total_processing_time", is(histories.get(0).getTotalProcessingTime())));
    verify(historyService, times(1)).getHistory();
  }

  public static History createHistory() {
    History history = new History();
    history.setFrequentWord("something");
    history.setAverageParagraphSize("3");
    history.setAverageParagraphProcessingTime("1.00 s");
    history.setTotalProcessingTime("4.00 s");
    history.setCreatedAt(LocalDateTime.now());
    return history;
  }
}
