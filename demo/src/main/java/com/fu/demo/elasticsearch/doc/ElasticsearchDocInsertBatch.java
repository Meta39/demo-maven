package com.fu.demo.elasticsearch.doc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fu.demo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.xcontent.XContentType;

import java.io.IOException;

/**
 * 文档批量插入
 */
@Slf4j
public class ElasticsearchDocInsertBatch {
    public static void main(String[] args) throws IOException {
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost",9200,"http"))
        );
        //插入数据
        BulkRequest request = new BulkRequest();
        request.add(new IndexRequest().index("user").id("1001").source(XContentType.JSON,"name","张三","phone","110"));
        request.add(new IndexRequest().index("user").id("1002").source(XContentType.JSON,"name","李四","phone","120"));
        request.add(new IndexRequest().index("user").id("1003").source(XContentType.JSON,"name","王五","phone","119"));
        request.add(new IndexRequest().index("user").id("1004").source(XContentType.JSON,"name","赵六","phone","110"));
        request.add(new IndexRequest().index("user").id("1005").source(XContentType.JSON,"name","王八","phone","120"));

        BulkResponse response = esClient.bulk(request,RequestOptions.DEFAULT);
        log.info("Took：{}",response.getTook());
        log.info("Items：{}",response.getItems());

        esClient.close();
    }
}
