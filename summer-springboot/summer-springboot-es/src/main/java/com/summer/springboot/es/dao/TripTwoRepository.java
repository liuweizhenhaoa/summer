package com.summer.springboot.es.dao;

import com.summer.springboot.es.model.TripTwo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TripTwoRepository extends ElasticsearchRepository<TripTwo, String> {

    TripTwo queryTripById(String id);

}
