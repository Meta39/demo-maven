package com.fu.demo.elasticsearch.doc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fu.demo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.xcontent.XContentType;

import java.io.IOException;

/**
 * 文档新增
 */
@Slf4j
public class ElasticsearchDocInsert {

    public static void main(String[] args) throws IOException {
        RestHighLevelClient esClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost",9200,"http"))
        );
        //插入数据
        IndexRequest request = new IndexRequest();
        request.index("user").id("1001");

        User user = new User();
        user.setName("zhangsan");
        user.setRemark("男");

        //向Elasticsearch插入数据必须是JSON格式
        ObjectMapper om = new ObjectMapper();
        String userJson = om.writeValueAsString(user);
        request.source(userJson, XContentType.JSON);

        IndexResponse response = esClient.index(request, RequestOptions.DEFAULT);

        log.info("插入数据：{}",response.getResult());

        esClient.close();
    }

}
