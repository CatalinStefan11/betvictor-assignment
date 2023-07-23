package com.betvictor.processor.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

import java.text.DecimalFormat;

@ToString
public class ParagraphResponse {
  @JsonProperty(value = "freq_word")
  private String frequentWord;

  @JsonProperty(value = "avg_paragraph_size")
  private String averageParagraphSize;

  @JsonProperty(value = "avg_paragraph_processing_time")
  private String averageParagraphProcessingTime;

  @JsonProperty(value = "total_processing_time")
  private String totalProcessingTime;

  private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.###");

  public ParagraphResponse(String frequentWord, String averageParagraphSize,
      String averageParagraphProcessingTime, String totalProcessingTime) {
    this.frequentWord = frequentWord;
    this.averageParagraphSize = averageParagraphSize;
    this.averageParagraphProcessingTime = formatDuration(averageParagraphProcessingTime);
    this.totalProcessingTime = formatDuration(totalProcessingTime);
  }

  public String getFrequentWord() {
    return frequentWord;
  }

  public void setFrequentWord(String frequentWord) {
    this.frequentWord = frequentWord;
  }

  public String getAverageParagraphSize() {
    return averageParagraphSize;
  }

  public void setAverageParagraphSize(String averageParagraphSize) {
    this.averageParagraphSize = averageParagraphSize;
  }

  public String getAverageParagraphProcessingTime() {
    return averageParagraphProcessingTime;
  }

  public void setAverageParagraphProcessingTime(String averageParagraphProcessingTime) {
    this.averageParagraphProcessingTime = formatDuration(averageParagraphProcessingTime);
  }

  public String getTotalProcessingTime() {
    return totalProcessingTime;
  }

  public void setTotalProcessingTime(String totalProcessingTime) {
    this.totalProcessingTime = formatDuration(totalProcessingTime);
  }

  private String formatDuration(String duration) {
    double timeInSeconds = Double.parseDouble(duration);
    if (timeInSeconds < 1) {
      return DECIMAL_FORMAT.format(timeInSeconds * 1000) + " ms";
    } else {
      return DECIMAL_FORMAT.format(timeInSeconds) + " s";
    }
  }
}