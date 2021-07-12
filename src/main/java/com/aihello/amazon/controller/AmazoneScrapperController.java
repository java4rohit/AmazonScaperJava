package com.aihello.amazon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aihello.amazon.model.ScapperKeywordResponse;
import com.aihello.amazon.model.ScraperKeywordRequest;
import com.aihello.amazon.service.AmazonScrapperService;

@RestController
@RequestMapping("/amazon/scrapper")
public class AmazoneScrapperController {

	@Autowired
	AmazonScrapperService amazonScrapperService;

	@GetMapping(value = "/keywards", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ScapperKeywordResponse> getScapperKeywordResponse(@RequestParam String mid,String alias, String fresh,String ks,String prefix,String event,String limit,String keyward) {
		
		ScraperKeywordRequest request = new ScraperKeywordRequest();
		request.setAlias(alias);
		request.setEvent(event);
		request.setFresh(fresh);
		request.setKeyward(keyward);
		request.setKs(ks);
		request.setLimit(limit);
		request.setMid(mid);
		request.setPrefix(prefix);
		
		ScapperKeywordResponse response = amazonScrapperService.requestKeyWords(request);
		return new ResponseEntity<ScapperKeywordResponse>(response,
				response != null ? HttpStatus.OK : HttpStatus.NO_CONTENT);
	}

	@GetMapping(value = "/metadata", produces = MediaType.ALL_VALUE)
	public ResponseEntity<byte[]> getMetaData() {
		byte[] response = amazonScrapperService.keywordMetadata();
		return new ResponseEntity<byte[]>(response, response != null ? HttpStatus.OK : HttpStatus.NO_CONTENT);
	}

}
