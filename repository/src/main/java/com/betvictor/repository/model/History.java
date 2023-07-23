package com.betvictor.repository.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Document
public class History {

    @Id
    @JsonIgnore
    private String id;
    @JsonProperty(value = "freq_word")
    private String frequentWord;
    @JsonProperty(value = "avg_paragraph_size")
    private String averageParagraphSize;
    @JsonProperty(value = "avg_paragraph_processing_time")
    private String averageParagraphProcessingTime;
    @JsonProperty(value = "total_processing_time")
    private String totalProcessingTime;
    @JsonIgnore
    private LocalDateTime createdAt;
}