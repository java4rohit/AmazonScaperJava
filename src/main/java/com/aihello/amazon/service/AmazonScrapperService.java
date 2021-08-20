package com.aihello.amazon.service;

import java.io.IOException;
import java.util.List;

import com.aihello.amazon.entities.KeywordSuggestion;
import com.aihello.amazon.model.KeywordResponse;
import com.aihello.amazon.model.ScraperKeywordRequest;
import com.aihello.amazon.model.Suggestions;

public interface AmazonScrapperService {

	public KeywordResponse requestKeyWords(ScraperKeywordRequest request);
//	public byte[] keywordMetadata();

	public List<KeywordResponse> getKeyword();

	//public Suggestions keywordMetadata(Suggestions keyword) throws IOException;

	public KeywordSuggestion keywordMetadata(String suggested_keyword, int id) throws IOException;

	//public String keywordMetadata(String keyword) throws IOException;

}
