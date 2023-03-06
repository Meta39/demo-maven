package com.fu.demo.controller;

import com.fu.demo.mongo.Apple;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("mongo")
public class MongoController {

    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * 新增数据
     * @param apple 对象
     */
    @PostMapping ("insert")
    public void insert(@RequestBody @Valid Apple apple){
        mongoTemplate.insert(apple);
    }

    /**
     * 通过id查询一条数据
     * @param id id
     * @return
     */
    @GetMapping("findById")
    public Apple findById(@RequestParam Long id){
        return mongoTemplate.findById(id,Apple.class);
    }

    /**
     * 查询全部集合数据
     * @return
     */
    @GetMapping("find")
    public List<Apple> find(){
        return mongoTemplate.find(new Query(),Apple.class);
    }

    /**
     * 修改一条数据
     * @param apple 对象
     */
    @PostMapping("update")
    public void update(@RequestBody @Valid Apple apple){
        Query query=new Query(Criteria.where("_id").is(apple.getId()));
        Update update= new Update();
        //id是无法修改的因为要先通过id来查询数据，查询完数据以后替换掉除id以外的内容
//        update.set("_id",apple.getId());
        update.set("name",apple.getName());
        mongoTemplate.updateFirst(query,update,Apple.class);
    }

    /**
     * 通过id删除一条数据
     * @param id id
     */
    @PostMapping("delete")
    public void delete(@RequestParam Long id){
        Query query=new Query(Criteria.where("_id").is(id));
        mongoTemplate.remove(query,Apple.class);
    }

}
