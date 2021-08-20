package com.aihello.amazon.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class KeywordResponse {

	private int id;
	private String keyword;
	
	 @JsonInclude(Include.NON_NULL)
	private List<Suggestions> suggestionsList;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	

	public List<Suggestions> getSuggestionsList() {
		return suggestionsList;
	}

	public void setSuggestionsList(List<Suggestions> suggestionsList) {
		this.suggestionsList = suggestionsList;
	}

	@Override
	public String toString() {
		return "KeywordResponse [id=" + id + ", keyword=" + keyword + "]";
	}

}
