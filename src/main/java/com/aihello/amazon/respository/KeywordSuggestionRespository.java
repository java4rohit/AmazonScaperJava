package com.aihello.amazon.respository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.aihello.amazon.entities.KeywordSuggestion;



@Repository
public interface KeywordSuggestionRespository extends CrudRepository<KeywordSuggestion,Integer> {

}
