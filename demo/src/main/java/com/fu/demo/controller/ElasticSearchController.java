package com.fu.demo.controller;

import com.fu.demo.entity.ElasticSearchEntity;
import com.fu.demo.service.ElasticSearchService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("elasticsearch")
public class ElasticSearchController {
    @Resource
    private ElasticSearchService elasticSearchService;

    /**
     * 新增/修改，如果id一致就修改，否则新增
     * @param elasticSearchEntity 实体类
     */
    @PostMapping
    public void save(@RequestBody final ElasticSearchEntity elasticSearchEntity) {
        elasticSearchService.save(elasticSearchEntity);
    }

    /**
     * 通过ID查询
     * @param id ID
     * @return
     */
    @GetMapping("/{id}")
    public ElasticSearchEntity findById(@PathVariable final String id) {
        return elasticSearchService.findById(id);
    }

    /**
     * 通过ID删除
     * @param id ID
     * @return
     */
    @DeleteMapping("/{id}")
    public boolean deleteById(@PathVariable String id) {
        elasticSearchService.deleteById(id);
        return true;
    }

    /**
     * 通过名称模糊查询
     * @param name 名称
     * @return
     */
    @GetMapping("/name/{name}")
    public List<ElasticSearchEntity> findAllByName(@PathVariable String name) {
        return elasticSearchService.findByName(name);
    }

    /**
     * 使用注释的方式名称模糊查询，即：@Query()注解
     * @param name 名称
     * @return
     */
    @GetMapping("/name/{name}/annotations")
    public List<ElasticSearchEntity> findAllByNameAnnotations(@PathVariable String name) {
        return elasticSearchService.findAllByNameUsingAnnotations(name);
    }

    /**
     * 简单分页查询
     * @param page 起始页，默认从0开始
     * @param size 每页数量
     * @return
     */
    @GetMapping("/page")
    public Page<ElasticSearchEntity> findAllByPage(@RequestParam(name = "page",defaultValue = "0") int page, @RequestParam(name = "size",defaultValue = "10") int size){
        return elasticSearchService.findAllByPage(page,size);
    }

}
