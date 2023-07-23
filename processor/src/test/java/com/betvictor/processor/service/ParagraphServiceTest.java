package com.betvictor.processor.service;

import com.betvictor.processor.client.ParagraphClient;
import com.betvictor.processor.model.ParagraphResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ParagraphServiceTest {

  @Mock
  private ParagraphClient paragraphClient;

  @Mock
  private KafkaParagraphProducer kafkaParagraphProducer;

  @InjectMocks
  private ParagraphService paragraphService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testProcessRandomText() {
    when(paragraphClient.getRandomText(anyInt(), anyString())).thenReturn(getParagraphResponse());

    ParagraphResponse response = paragraphService.processRandomText(2, "short");

    assertEquals("is", response.getFrequentWord());
    assertEquals("8", response.getAverageParagraphSize());
    verify(kafkaParagraphProducer, times(1)).produceMessage(any());
  }

  @Test
  void testSendRequestAndProcess() {
    when(paragraphClient.getRandomText(anyInt(), anyString())).thenReturn(getParagraphResponse());

    List<String[]> paragraphWords = paragraphService.sendRequestAndProcess(2, "short");

    // 2 requests of 2 paragraphs each
    assertEquals(4, paragraphWords.size());
  }

  private String getParagraphResponse() {
    return """
           <p>This is paragraph 1 that is for testing.</p>
           <p>This is paragraph 2 that is for testing.</p>
        """;
  }
}
