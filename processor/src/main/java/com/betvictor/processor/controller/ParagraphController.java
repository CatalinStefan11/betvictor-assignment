package com.betvictor.processor.controller;

import com.betvictor.processor.model.ParagraphResponse;
import com.betvictor.processor.service.ParagraphService;
import com.betvictor.processor.validation.ValidParagraphLength;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.betvictor.processor.util.Constants.PARAGRAPH_ERROR_MESSAGE;

@Validated
@RestController
public class ParagraphController {
  
  private ParagraphService paragraphService;

  public ParagraphController(ParagraphService paragraphService) {
    this.paragraphService = paragraphService;
  }

  @GetMapping(value = "/betvictor/text", produces = MediaType.APPLICATION_JSON_VALUE)
  public ParagraphResponse getParagraph(
      @Valid @RequestParam("p") @Min(value = 1, message = PARAGRAPH_ERROR_MESSAGE) int numberOfParagraphs,
      @Valid @RequestParam("l") @ValidParagraphLength String paragraphLength) {
    return paragraphService.processRandomText(numberOfParagraphs, paragraphLength);
  }
}