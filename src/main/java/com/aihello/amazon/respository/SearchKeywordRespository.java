package com.aihello.amazon.respository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.aihello.amazon.entities.SearchKeyword;


@Repository
public interface SearchKeywordRespository  extends CrudRepository<SearchKeyword, Integer> {
	

}
