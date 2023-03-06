package com.fu.demo.repository;

import com.fu.demo.entity.ElasticSearchEntity;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

//ElasticsearchRepository<实体类,主键类型>
public interface ElasticSearchRepo extends ElasticsearchRepository<ElasticSearchEntity,String> {
    List<ElasticSearchEntity> findByName(String name);

    @Query("{\"match\":{\"name\":\"?0\"}}")
    List<ElasticSearchEntity> findAllByNameUsingAnnotations(String name);
}
