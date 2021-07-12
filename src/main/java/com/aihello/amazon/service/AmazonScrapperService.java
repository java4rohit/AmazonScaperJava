package com.aihello.amazon.service;

import com.aihello.amazon.model.ScapperKeywordResponse;
import com.aihello.amazon.model.ScraperKeywordRequest;

public interface AmazonScrapperService {

	public ScapperKeywordResponse requestKeyWords(ScraperKeywordRequest request);
	public byte[] keywordMetadata();
	
	
	
}
