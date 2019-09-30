package com.summer.springboot.es.dao;

import com.summer.springboot.es.model.Trip;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripRepository extends ElasticsearchRepository<Trip, String> {

    Trip queryTripById(String id);

}
