package com.aihello.amazon.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class SearchKeyword {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String keyword;
	private Date date;

	@OneToMany(cascade = CascadeType.ALL) 
	@JoinColumn(name = "keyword_Id", referencedColumnName = "id")
	private List<KeywordSuggestion> keywordSuggestion;

	public SearchKeyword() {
	}

	public SearchKeyword(int id, String keyword, Date date, List<KeywordSuggestion> keywordSuggestion) {
		super();
		this.id = id;
		this.keyword = keyword;
		this.date = date;
		this.keywordSuggestion = keywordSuggestion;
	}

	public String getKeyword() {
		return keyword;
	}

	public SearchKeyword setKeyword(String keyword) {
		this.keyword = keyword;
		return this;
	}

	public Date getDate() {
		return date;
	}

	public SearchKeyword setDate(Date date) {
		this.date = date;
		return this;
	}

	public int getId() {
		return id;
	}

	public SearchKeyword setId(int id) {
		this.id = id;
		return this;
	}

	public List<KeywordSuggestion> getKeywordSuggestion() {
		return keywordSuggestion;
	}

	public SearchKeyword setKeywordSuggestion(List<KeywordSuggestion> keywordSuggestion) {
		this.keywordSuggestion = keywordSuggestion;
		return this;
	}

	@Override
	public String toString() {
		return "SearchKeyword [id=" + id + ", keyword=" + keyword + ", date=" + date + ", keywordSuggestion="
				+ keywordSuggestion + "]";
	}

}
