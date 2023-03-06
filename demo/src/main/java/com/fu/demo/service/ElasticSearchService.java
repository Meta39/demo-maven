package com.fu.demo.service;

import com.fu.demo.entity.ElasticSearchEntity;
import com.fu.demo.repository.ElasticSearchRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ElasticSearchService {
    @Resource
    private ElasticSearchRepo elasticSearchRepo;

    /**
     * 新增/修改
     */
    public void save(final ElasticSearchEntity elasticSearchEntity) {
        elasticSearchRepo.save(elasticSearchEntity);
    }

    /**
     * 通过ID查询
     */
    public ElasticSearchEntity findById(final String id){
        return elasticSearchRepo.findById(id).orElse(null);
    }

    /**
     * 通过ID删除
     */
    public void deleteById(String id){
        elasticSearchRepo.deleteById(id);
    }

    /**
     * 通过名称模糊查询
     */
    public List<ElasticSearchEntity> findByName(final String name){
        return elasticSearchRepo.findByName(name);
    }

    /**
     * 通过使用注解名称模糊查询
     */
    public List<ElasticSearchEntity> findAllByNameUsingAnnotations(String name){
        return elasticSearchRepo.findAllByNameUsingAnnotations(name);
    }

    /**
     * 分页
     * @param size 数量
     */
    public Page<ElasticSearchEntity> findAllByPage(int page,int size){
        //Sort sort = Sort.by(Sort.Order.desc("create_date"));//排序
        //Pageable pageable =PageRequest.of(Integer.parseInt(page), Integer.parseInt(size), sort);
        Sort sort = Sort.by(Sort.Order.desc("createTime"));//根据
        Pageable pageable =PageRequest.of(page, size, sort);
        return elasticSearchRepo.findAll(pageable);
    }
}
