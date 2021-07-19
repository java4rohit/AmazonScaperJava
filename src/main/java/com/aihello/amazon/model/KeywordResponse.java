package com.aihello.amazon.model;

public class KeywordResponse {

	int id;
	String keyword;

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

	@Override
	public String toString() {
		return "KeywordResponse [id=" + id + ", keyword=" + keyword + "]";
	}

}
