package com.betvictor.repository.service;

import com.betvictor.repository.model.History;
import com.betvictor.repository.repository.HistoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HistoryServiceImplTest {

    @Mock
    private HistoryRepository historyRepository;

    @InjectMocks
    private HistoryService historyService;

    @Test
    public void testHistoryServiceAddToDatabase() {
        History history = createHistory();
        when(historyRepository.save(history)).thenReturn(any());
        historyService.addHistory(history);
        verify(historyRepository, times(1)).save(history);
    }

    @Test
    public void testHistoryServiceGetFromDatabase() {
        when(historyRepository.findAll(any(Pageable.class)))
            .thenReturn(new PageImpl<>(List.of(createHistory())));
        List<History> result = historyService.getHistory();
        verify(historyRepository, times(1)).findAll(any(Pageable.class));
        assertEquals(result.size(), 1);
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
