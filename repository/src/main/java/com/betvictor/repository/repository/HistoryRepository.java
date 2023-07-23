package com.betvictor.repository.repository;

import com.betvictor.repository.model.History;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryRepository extends MongoRepository<History, String> {

}