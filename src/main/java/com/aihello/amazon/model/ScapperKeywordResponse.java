package com.aihello.amazon.model;

import java.util.ArrayList;

public class ScapperKeywordResponse {

	private String mid;
	private String alias;
	private String prefix;
	private String suffix;
	private ArrayList<Suggestions> suggestions;
	private String suggestionTitleId;
	private String responseId;
	private String shuffled;

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public ArrayList<Suggestions> getSuggestions() {
		return suggestions;
	}

	public void setSuggestions(ArrayList<Suggestions> suggestions) {
		this.suggestions = suggestions;
	}

	public String getSuggestionTitleId() {
		return suggestionTitleId;
	}

	public void setSuggestionTitleId(String suggestionTitleId) {
		this.suggestionTitleId = suggestionTitleId;
	}

	public String getResponseId() {
		return responseId;
	}

	public void setResponseId(String responseId) {
		this.responseId = responseId;
	}

	public String getShuffled() {
		return shuffled;
	}

	public void setShuffled(String shuffled) {
		this.shuffled = shuffled;
	}

	@Override
	public String toString() {
		return "ScapperKeywordResponse [mid=" + mid + ", alias=" + alias + ", prefix=" + prefix + ", suffix=" + suffix
				+ ", suggestions=" + suggestions + ", suggestionTitleId=" + suggestionTitleId + ", responseId="
				+ responseId + ", shuffled=" + shuffled + "]";
	}

}
