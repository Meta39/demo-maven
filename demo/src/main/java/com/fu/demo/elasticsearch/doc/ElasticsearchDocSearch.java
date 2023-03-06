package com.fu.demo.elasticsearch.doc;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

/**
 * 文档全量查询
 */
@Slf4j
public class ElasticsearchDocSearch {

    public static void main(String[] args) throws IOException {
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost",9200,"http"))
        );
        //获取数据
        SearchRequest request = new SearchRequest();
        request.indices("user");
        //构造查询条件
        request.source(new SearchSourceBuilder().query(QueryBuilders.matchAllQuery()));
        SearchResponse response = esClient.search(request, RequestOptions.DEFAULT);
        SearchHits searchHits = response.getHits();
        log.info("全量查询文档{}",searchHits.getTotalHits());
        log.info("全量查询文档时间{}",response.getTook());
        for (SearchHit hit:searchHits){
            log.info(hit.getSourceAsString());
        }

        esClient.close();
    }

}
