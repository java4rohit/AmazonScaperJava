package com.aihello.amazon.service.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.aihello.amazon.model.ScapperKeywordResponse;
import com.aihello.amazon.model.ScraperKeywordRequest;
import com.aihello.amazon.service.AmazonScrapperService;

@Service
public class AmazonScrapperServiceImpl implements AmazonScrapperService {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${amazon.scrapper.keyward.url}")
	private String keywardURL;

	@Value("${amazon.scrapper.metadata.url}")
	private String metadataUrl;

	@Override
	public ScapperKeywordResponse requestKeyWords(ScraperKeywordRequest request) {

		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.add("Accept", org.springframework.http.MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<Void> requestEntity = new HttpEntity<>(requestHeaders);

		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(keywardURL)
				.queryParam("mid", request.getMid()).queryParam("alias", request.getAlias())
				.queryParam("fresh", request.getAlias()).queryParam("prefix", request.getPrefix())
				.queryParam("event", request.getEvent()).queryParam("limit", request.getLimit());

		ResponseEntity<ScapperKeywordResponse> responseEntity = restTemplate.exchange(uriBuilder.toUriString(),
				HttpMethod.GET, requestEntity, ScapperKeywordResponse.class);

		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			System.out.println("response received");
			System.out.println(responseEntity.getBody());
		} else {
			System.out.println("error occurred");
			System.out.println(responseEntity.getStatusCode());
		}
		return responseEntity.getBody();

	}

	@Override
	public byte[] keywordMetadata() {

		HttpHeaders requestHeaders = new HttpHeaders();

		requestHeaders.setAccept(Arrays.asList(MediaType.TEXT_HTML));
		requestHeaders.add("Origin", "https://www.amazon.com");
		requestHeaders.add("Referer", "https://www.amazon.com/");
		requestHeaders.add("Accept-Encoding", "gzip");

		HttpEntity<Void> requestEntity = new HttpEntity<>(requestHeaders);

		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl("https://www.amazon.com/")
				.queryParam("i", "aps").queryParam("k", "keyword").queryParam("ref", "nb_sb_noss")
				.queryParam("url", "search-alias=aps");

		URI uri = null;
		try {
			uri = new URI(uriBuilder.toString());
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}

		ResponseEntity<byte[]> responseEntity = null;
		try {

			restTemplate.headForHeaders("https://www.amazon.com/s", uriBuilder);

			responseEntity = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, byte[].class);

			if (responseEntity.getStatusCode() == HttpStatus.OK) {
				System.out.println("response received");
				System.out.println(responseEntity.getBody());
			} else {
				System.out.println("error occurred");
				System.out.println(responseEntity.getStatusCode());
			}
		} catch (RestClientException e) {
			e.printStackTrace();
		}

		if (responseEntity == null)
			return null;

		return responseEntity.getBody();

	}

}
