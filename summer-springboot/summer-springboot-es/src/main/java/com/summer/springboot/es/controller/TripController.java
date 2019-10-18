package com.summer.springboot.es.controller;

import com.summer.springboot.es.dao.TripRepository;
import com.summer.springboot.es.dao.TripTwoRepository;
import com.summer.springboot.es.model.Trip;
import com.summer.springboot.es.model.TripTwo;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.Date;
import java.util.List;

@RestController
public class TripController {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private TripTwoRepository tripTwoRepository;


    @GetMapping("new")
    public Object newTrip(@RequestParam(defaultValue = "SpringBootElasticSearch") String title, @RequestParam(defaultValue = "`Elasticsearch` 是一个`开源`的`分布式`、`高扩展`、`高实时`的RESTful `搜索和分析引擎`，基于`Lucene`......") String text) {
        //构建并保存Trip
        Trip trip = new Trip();
        trip.setId("10001");
        trip.setUserId("u1111111");
        trip.setUserPhone("1111");
        trip.setGenDate(new Date());
        trip.setBikeId("1111");
        return trip;
    }

    @GetMapping("save")
    public Object save() {
        //构建并保存Trip
        Trip trip = new Trip();
        trip.setId("10001");
        trip.setUserId("u1111111");
        trip.setUserPhone("1111");
        trip.setGenDate(new Date());
        trip.setBikeId("1111");
        tripRepository.save(trip);

        //构建并保存Trip
        TripTwo tripTwo = new TripTwo();
        tripTwo.setId("10001");
        tripTwo.setUserId("1");
        tripTwo.setUserPhone("1");
        tripTwo.setGenDate(new Date());
        tripTwo.setBikeId("1111");
        tripTwoRepository.save(tripTwo);
        return trip;
    }

    //查询
    @RequestMapping("/query/{id}")
    public Object query(@PathVariable("id") String id) {

        Trip accountInfo = tripRepository.queryTripById(id);

        return accountInfo;
    }


    /**
     * ElasticSearch之Search封装查询
     *
     * @param name     搜索标题
     * @param pageable page = 第几页参数(第一页是0), value = 每页显示条数
     * @author zhengkai.blog.csdn.net
     */
    @GetMapping("search")
    public Object search(@RequestParam(defaultValue = "社区") String name, @PageableDefault(page = 0, value = 10) Pageable pageable) {
        //以下查询等同于封装了{"query":{"bool":{"must":[{"wildcard":{"title.keyword":{"wildcard":"*SpringBoot*","boost":1}}}],"disable_coord":false,"adjust_pure_negative":true,"boost":1}}}
        //按标题进行模糊查询
        QueryBuilder queryBuilder = QueryBuilders.wildcardQuery("HIRE_STATION_NAME_", "*社区*");
        //按照顺序构建builder,bool->must->wildcard ,有了上文的JSON,顺序就很好理解了
        BoolQueryBuilder must = QueryBuilders.boolQuery().must(queryBuilder);
        //封装pageable分页
        Page<Trip> queryResult = tripRepository.search(must, pageable);
        //返回
        return queryResult;
    }

    /**
     * ElasticSearch之elasticsearchTemplate查询
     *
     * @param name 搜索标题
     * @author zhengkai.blog.csdn.net
     */
    @GetMapping("originSearch")
    public Object originSearch(@RequestParam(defaultValue = "社区") String name) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        QueryBuilder queryBuilder = QueryBuilders.wildcardQuery("HIRE_STATION_NAME_.keyword", "*社区*");
        BoolQueryBuilder must = boolQuery.must(queryBuilder);
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        NativeSearchQuery build = nativeSearchQueryBuilder.withQuery(must).build();
        List<Trip> queryForList = elasticsearchTemplate.queryForList(build, Trip.class);

        return queryForList;
    }


}
