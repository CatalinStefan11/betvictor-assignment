package com.betvictor.processor.controller;

import com.betvictor.processor.client.ParagraphClient;
import com.betvictor.processor.service.KafkaParagraphProducer;
import com.betvictor.processor.service.ParagraphService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ParagraphControllerTest {
    
    MockMvc mockMvc;

    @Mock
    private ParagraphClient paragraphClient;

    @Mock
    private KafkaParagraphProducer kafkaParagraphProducer;

    @InjectMocks
    @Spy
    private ParagraphService paragraphService;
    
    ParagraphController paragraphController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        paragraphController = new ParagraphController(paragraphService);
        mockMvc = MockMvcBuilders.standaloneSetup(paragraphController).build();
    }

    @Test
    public void testParagraphController() throws Exception {
        when(paragraphClient.getRandomText(anyInt(), anyString())).thenReturn(getParagraphResponse());
     
        mockMvc.perform(get("/betvictor/text?p=10&l=long")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.freq_word", is("is")))
                .andExpect(jsonPath("$.avg_paragraph_size", is("8")))
                .andExpect(jsonPath("$.avg_paragraph_processing_time", is(anything())))
                .andExpect(jsonPath("$.total_processing_time", is(anything())));
        verify(paragraphService, times(1)).processRandomText(10, "long");
        verify(kafkaParagraphProducer, times(1)).produceMessage(any());
    }

    private String getParagraphResponse() {
        return """
           <p>This is paragraph 1 that is for testing.</p>
           <p>This is paragraph 2 that is for testing.</p>
        """;
    }
}