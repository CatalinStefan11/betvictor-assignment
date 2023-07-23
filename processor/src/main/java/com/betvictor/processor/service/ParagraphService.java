package com.betvictor.processor.service;

import com.betvictor.processor.client.ParagraphClient;
import com.betvictor.processor.model.ParagraphResponse;
import com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.betvictor.processor.util.WordCalculator.calculateAverageParagraphSize;
import static com.betvictor.processor.util.WordCalculator.calculateMostFrequentWord;

@Slf4j
@Service
public class ParagraphService {

  private ParagraphClient paragraphClient;
  private KafkaParagraphProducer kafkaParagraphProducer;

  public ParagraphService(ParagraphClient paragraphClient, KafkaParagraphProducer kafkaParagraphProducer) {
    this.paragraphClient = paragraphClient;
    this.kafkaParagraphProducer = kafkaParagraphProducer;
  }

  public ParagraphResponse processRandomText(int paragraphRequests, String paragraphLength) {
    log.info("Processing {} paragraph requests with {} paragraph length"
        , paragraphRequests, paragraphLength);
    
    StopWatch stopWatch = createWatchAndStart();
    
    List<String[]> paragraphWords = sendRequestAndProcess(paragraphRequests, paragraphLength);
    
    String mostFrequentWord = calculateMostFrequentWord(paragraphWords);
    int averageParagraphSize = calculateAverageParagraphSize(paragraphWords);
    double totalProcessingTime = getTotalTimeSeconds(stopWatch);

    ParagraphResponse response =  new ParagraphResponse(
        mostFrequentWord,
        String.valueOf(averageParagraphSize),
        String.valueOf(computeAverageTimePerParagraph(totalProcessingTime, paragraphWords.size())),
        String.valueOf(totalProcessingTime)
    );
    
    kafkaParagraphProducer.produceMessage(response);
    return response;
  }
  
  @VisibleForTesting
  List<String[]> sendRequestAndProcess(int paragraphRequests, String paragraphLength){
    return IntStream.range(1, paragraphRequests + 1)
        .parallel()
        .mapToObj(i -> paragraphClient.getRandomText(i, paragraphLength))
        .flatMap(this::convertResponse)
        .map((x) -> x.split("\\s+"))
        .toList();
  }

  private Stream<String> convertResponse(String response) {
    Document doc = Jsoup.parse(response);
    return doc.select("p").stream().map(Element::text);
  }

  private double computeAverageTimePerParagraph(double totalProcessingTime, int totalParagraphs) {
    return totalParagraphs > 0 ? totalProcessingTime / totalParagraphs : 0;
  }

  private StopWatch createWatchAndStart(){
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    return stopWatch;
  }

  private double getTotalTimeSeconds(StopWatch stopWatch){
    stopWatch.stop();
    return stopWatch.getTotalTimeSeconds();
  }
}
