package com.aihello.amazon.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
@Entity
public class KeywordSuggestion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String suggested_keyword;
	private String spellcorrected;
	private String strategyid;
	private String suggestionType;
	
	public int getId() {
		return id;
	}

	public KeywordSuggestion setId(int id) {
		this.id = id;
		return this;
	}

	public String getSuggested_keyword() {
		return suggested_keyword;
	}

	public KeywordSuggestion setSuggested_keyword(String suggested_keyword) {
		this.suggested_keyword = suggested_keyword;
		return this;
	}

	public String getSpellcorrected() {
		return spellcorrected;
	}

	public KeywordSuggestion setSpellcorrected(String spellcorrected) {
		this.spellcorrected = spellcorrected;
		return this;
	}

	public String getStrategyid() {
		return strategyid;
	}

	public KeywordSuggestion setStrategyid(String strategyid) {
		this.strategyid = strategyid;
		return this;
	}

	public String getSuggestionType() {
		return suggestionType;
	}

	public KeywordSuggestion setSuggestionType(String suggestionType) {
		this.suggestionType = suggestionType;
		return this;
	}

 
}
