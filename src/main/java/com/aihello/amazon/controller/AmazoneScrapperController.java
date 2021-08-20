package com.aihello.amazon.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aihello.amazon.entities.KeywordSuggestion;
import com.aihello.amazon.model.KeywordResponse;
import com.aihello.amazon.model.ScraperKeywordRequest;
import com.aihello.amazon.model.Suggestions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.aihello.amazon.service.AmazonScrapperService;

@RestController
@RequestMapping("/amazon/scrapper")
public class AmazoneScrapperController {

	@Autowired
	AmazonScrapperService amazonScrapperService;
	private static final Logger log = LoggerFactory.getLogger(AmazoneScrapperController.class);


    @GetMapping(value = "/getSuggestions", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<KeywordResponse> getScapperKeywordResponse(@RequestParam String mid, String alias, String fresh, String ks, String prefix, String event, String limit, String keyword,String country) {

        ScraperKeywordRequest request = new ScraperKeywordRequest();
        request.setAlias(alias);
        request.setEvent(event);
        request.setFresh(fresh);
        request.setKeyword(keyword);
        request.setKs(ks);
        request.setLimit(limit);
        request.setMid(mid);
        request.setPrefix(prefix);
        request.setCountry(country);

        KeywordResponse response = amazonScrapperService.requestKeyWords(request);
        return new ResponseEntity<KeywordResponse>(response,
            response != null ? HttpStatus.OK : HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/getKeywords", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<KeywordResponse>> getKeywords() {
        List<KeywordResponse> keywordList = amazonScrapperService.getKeyword();
        return new ResponseEntity<>(keywordList, keywordList.isEmpty() ? HttpStatus.NO_CONTENT:HttpStatus.OK);
    }
//	@GetMapping(value = "/metadata", produces = MediaType.ALL_VALUE)
//	public ResponseEntity<String> getMetaData() throws IOException {
//		String response = amazonScrapperService.keywordMetadata("iphone 12 pro max case");
//		return new ResponseEntity<String>(response, response != null ? HttpStatus.OK : HttpStatus.NO_CONTENT);
//	}

	@GetMapping(value = "/metadata", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<KeywordSuggestion> getMetaData(@RequestParam String suggested_keyword, int id)
			throws IOException {
        KeywordSuggestion response = amazonScrapperService.keywordMetadata(suggested_keyword, id);
		return new ResponseEntity<KeywordSuggestion>(response,
				response != null ? HttpStatus.OK : HttpStatus.NO_CONTENT);
	}

//	// @PostConstruct
//    public void getKeywordMeta() throws IOException {
//       String response = amazonScrapperService.keywordMetadata("soap nuts");
//        log.info(response.toString());
//    }

}
