package com.betvictor.processor.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "paragraphClient", url = "https://www.loripsum.net/api/")
public interface ParagraphClient {

  @RequestMapping(method = RequestMethod.GET,
      value = "{paragraphNumbers}/{paragraphLength}",
      produces = MediaType.TEXT_HTML_VALUE)
  String getRandomText(@PathVariable("paragraphNumbers") int p, 
                       @PathVariable("paragraphLength") String l);
  
}
