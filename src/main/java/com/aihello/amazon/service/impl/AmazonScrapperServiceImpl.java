package com.aihello.amazon.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.aihello.amazon.entities.KeywordSuggestion;
import com.aihello.amazon.entities.SearchKeyword;
import com.aihello.amazon.model.KeywordResponse;
import com.aihello.amazon.model.ScapperKeywordResponse;
import com.aihello.amazon.model.ScraperKeywordRequest;
import com.aihello.amazon.model.Suggestions;
import com.aihello.amazon.respository.KeywordSuggestionsRespository;
import com.aihello.amazon.respository.SearchKeywordRespository;
import com.aihello.amazon.service.AmazonScrapperService;

@Service
public class AmazonScrapperServiceImpl implements AmazonScrapperService {

	private static final Logger LOG = LoggerFactory.getLogger(AmazonScrapperServiceImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	KeywordSuggestionsRespository keywordSuggestionsRespository;

	@Autowired
	private SearchKeywordRespository searchKeywordRespository;

	@Value("${amazon.scrapper.keyword.url}")
	private String keywordURL;

	@Value("${amazon.scrapper.metadata.url}")
	private String metadataUrl;

	private Map<String, String> countryUrlMap;

	@PostConstruct
	private void initializeCountryUrlMap() {
		countryUrlMap = countryUrlMap();
	}

	/*
	 * This method checks the key into the database. If the key found in DB, then it
	 * prepares the client response and return; Else if keyword not found in
	 * database then Amazon API is called, amazon response is saved in DB and same
	 * is return to client
	 * 
	 * requestKeyWords== https://completion.amazon.com/api/2017/suggestions
	 */
	@Override
	public KeywordResponse requestKeyWords(ScraperKeywordRequest request) {

		String url = countryUrlMap.get(request.getCountry());
		if (null == url) {
			throw new IllegalArgumentException(
					"Country name is invalid, use one of the following country: " + countryUrlMap.keySet());
		}

		/*
		 * Check if keyword present in database
		 */
		Optional<SearchKeyword> searchKeywordOptional = searchKeywordRespository.findByKeyword(request.getPrefix());

		if (!searchKeywordOptional.isPresent()) {

			HttpHeaders requestHeaders = new HttpHeaders();
			requestHeaders.add("Accept", org.springframework.http.MediaType.APPLICATION_JSON_VALUE);

			HttpEntity<Void> requestEntity = new HttpEntity<>(requestHeaders);

			UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url).queryParam("mid", request.getMid())
					.queryParam("alias", request.getAlias()).queryParam("fresh", request.getAlias())
					.queryParam("prefix", request.getPrefix()).queryParam("event", request.getEvent())
					.queryParam("limit", request.getLimit()).queryParam("keyword", request.getKeyword());

			ResponseEntity<ScapperKeywordResponse> responseEntity = restTemplate.exchange(uriBuilder.toUriString(),
					HttpMethod.GET, requestEntity, ScapperKeywordResponse.class);

			if (responseEntity.getStatusCode() == HttpStatus.OK) {
				LOG.info("response received: {}", responseEntity.getBody());
				LOG.error("response ", responseEntity.getBody());
			}

			else {
				LOG.error("error occurred");
				LOG.info("StatusCode: {}", responseEntity.getStatusCode());
			}

			/*
			 * Prepare client Response
			 */
			KeywordResponse keywordResponse = new KeywordResponse();
			keywordResponse.setKeyword(responseEntity.getBody().getPrefix());

			List<Suggestions> keywordSuggestionList = new ArrayList<>();
			keywordSuggestionList.addAll(responseEntity.getBody().getSuggestions());
			keywordResponse.setSuggestionsList(keywordSuggestionList);

			SearchKeyword searchKeyword = new SearchKeyword();

			// Persist data in DB
			persistKewardSuggestion(responseEntity.getBody(), searchKeyword);

			keywordResponse.setId(searchKeyword.getId());
			return keywordResponse;

		}
		/*
		 * This block prepares client response
		 */
		else {
			SearchKeyword searchKeywordFromDB = searchKeywordOptional.get();

			// suggestionList for client
			ArrayList<Suggestions> suggestions = new ArrayList<>();

			List<KeywordSuggestion> keywordSuggestionListFromDB = searchKeywordFromDB.getKeywordSuggestion();

			for (KeywordSuggestion s : keywordSuggestionListFromDB) {
				Suggestions suggestion = new Suggestions();

				suggestion.setStrategyId(s.getStrategyid());
				suggestion.setSpellCorrected(s.getSpellcorrected());
				suggestion.setSuggType(s.getSuggestionType());
				suggestion.setValue(s.getSuggested_keyword());
				suggestion.setTotalCount(s.getTotalResultCount());

				suggestions.add(suggestion);

			}

			KeywordResponse keywordResponse = new KeywordResponse();
			keywordResponse.setId(searchKeywordFromDB.getId());
			keywordResponse.setKeyword(searchKeywordFromDB.getKeyword());
			keywordResponse.setSuggestionsList(suggestions);

			return keywordResponse;

		}

	}

	/*
	 * Save keyword and suggesionList to DB
	 */
	private void persistKewardSuggestion(ScapperKeywordResponse scapperKeywordResponse, SearchKeyword searchKeyword) {

		searchKeyword.setKeyword(scapperKeywordResponse.getPrefix());
		searchKeyword.setDate(new Date());

		List<Suggestions> suggestionList = scapperKeywordResponse.getSuggestions();
		// Copy above list into below
		List<KeywordSuggestion> keywordSuggestionList = new ArrayList<>();

		suggestionList.stream().forEach(s -> {
			KeywordSuggestion ks = new KeywordSuggestion();

			ks.setSpellcorrected(s.getSpellCorrected());
			ks.setStrategyid(s.getStrategyId());
			ks.setSuggested_keyword(s.getValue());
			ks.setSuggestionType(s.getSuggType());

			keywordSuggestionList.add(ks);
		});

		searchKeyword.setKeywordSuggestion(keywordSuggestionList);
		searchKeywordRespository.save(searchKeyword);
	}

	/*
	 * Get Keyword List
	 */
	@Override
	public List<KeywordResponse> getKeyword() {

		List<SearchKeyword> searchkeywordList = (List<SearchKeyword>) searchKeywordRespository.findAll();

		List<KeywordResponse> responseList = new ArrayList<>();

		searchkeywordList.stream().forEach(searchkeyword -> {
			KeywordResponse keywordResponse = new KeywordResponse();
			BeanUtils.copyProperties(searchkeyword, keywordResponse);
			responseList.add(keywordResponse);
		});

		return responseList;
	}

	@Override
	public KeywordSuggestion keywordMetadata(String suggested_keyword, int id) throws IOException {

		Optional<KeywordSuggestion> keywordSuggestionsRespositoryOptional = keywordSuggestionsRespository.findById(id);

		if (!keywordSuggestionsRespositoryOptional.isPresent()) {
			throw new IllegalArgumentException();
		}
		String output = "";

		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl("https://www.amazon.com/s")
				.queryParam("i", "aps").queryParam("k", suggested_keyword).queryParam("ref", "nb_sb_noss")
				.queryParam("url", "search-alias=aps");

		URL myURL = new URL(uriBuilder.toUriString());
		HttpURLConnection myURLConnection = (HttpURLConnection) myURL.openConnection();

		myURLConnection.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_" + ThreadLocalRandom.current().nextInt(9, 15 + 1)
						+ "_1) AppleWebKit/531.36 (KHTML, like Gecko) Chrome/"
						+ ThreadLocalRandom.current().nextInt(70, 79 + 1) + ".0.3945.130 Safari/531.36");
		myURLConnection.setRequestProperty("Origin", "https://www.amazon.com");
		myURLConnection.setRequestProperty("Referer", "https://www.amazon.com/");
		myURLConnection.setRequestProperty("Accept-Encoding", "gzip");
		myURLConnection.setRequestProperty("Accept-Language", "en-US");
		myURLConnection.setRequestProperty("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		/*
		 * try (BufferedReader bufferedReader = new BufferedReader(new
		 * InputStreamReader(myURLConnection.getInputStream()))) { String line; while
		 * ((line = bufferedReader.readLine()) != null) { output += line; } }
		 */
		InputStream is = null;
		try {
			is = new GZIPInputStream(myURLConnection.getInputStream());
			byte[] byteChunk = new byte[4096]; // Or whatever size you want to read in at a time.
			int n;

			while ((n = is.read(byteChunk)) > 0) {
				baos.write(byteChunk, 0, n);
			}
		} catch (IOException e) {
			System.err.printf("Failed while reading bytes from %s: %s", myURL, e.getMessage());
			e.printStackTrace();
			// Perform any other exception handling that's appropriate.
		} finally {
			if (is != null) {
				is.close();
			}
		}
		byte[] allbytes = baos.toByteArray();

		output = new String(allbytes, StandardCharsets.UTF_8);

		Pattern p = Pattern.compile("(\\w*\"totalResultCount\":\\w*.[0-9])");
		Matcher matcher = p.matcher(output);

		matcher.find();

		KeywordSuggestion keywordSuggestion = keywordSuggestionsRespositoryOptional.get();
		keywordSuggestion.setTotalResultCount(matcher.group(1) != null ? matcher.group(1).split(":")[1] : null);

		keywordSuggestionsRespository.save(keywordSuggestion);

		return keywordSuggestion;

	}

	private Map<String, String> countryUrlMap() {
		Map<String, String> map = new HashMap<>();
		map.put("India", "https://completion.amazon.com/api/2017/suggestions");
		map.put("UnitedKingdon", "https://completion.amazon.co.uk/api/2017/suggestions");
		map.put("Brazilian ", "https://completion.amazon.com.br/api/2017/suggestions");
		map.put("Canada", "https://completion.amazon.ca/api/2017/suggestions");
		map.put("Arab Emirates", "https://completion.amazon.ae/api/2017/suggestions");
		map.put("UnitedKingdon", "https://completion.amazon.de/api/2017/suggestions");
		map.put("Netherlands", "https://completion.amazon.nl/api/2017/suggestions");
		map.put("Spain", "https://completion.amazon.es/api/2017/suggestions");
		map.put("Japan", "https://completion.amazon.jp/api/2017/suggestions");
		map.put("France", "https://completion.amazon.fr/api/2017/suggestions");
		map.put("Italy", "https://completion.amazon.it/api/2017/suggestions");
		map.put("Australia", "https://completion.amazon.com.au/api/2017/suggestions");
		map.put("Singapore", "https://completion.amazon.com.sg/api/2017/suggestions");

		return map;
	}

}
