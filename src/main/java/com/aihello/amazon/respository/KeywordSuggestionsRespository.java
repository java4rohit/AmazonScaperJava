package com.aihello.amazon.respository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.aihello.amazon.entities.KeywordSuggestion;
import com.aihello.amazon.entities.SearchKeyword;
@Repository
public interface KeywordSuggestionsRespository extends CrudRepository<KeywordSuggestion,Integer> {

 

	}
