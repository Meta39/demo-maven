package com.fu.springbootjpademo;

import com.fu.springbootjpademo.entity.Person;
import com.fu.springbootjpademo.repositories.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;

@Slf4j
@SpringBootTest
public class PersonRepositoryTests {
    @Resource
    private PersonRepository personRepository;

    /**
     * 新增/修改
     */
    @Test
    public void save(){
        Person person = new Person();
        person.setName("Meta");
        person.setSex(0);
        this.personRepository.save(person);
        log.info("{}",this.personRepository.save(person));
    }

    /**
     * 查询
     */
    @Test
    public void search(){
//        log.info("{}",this.personRepository.findById(1L));//使用框架自带的方法
//        log.info("{}",this.personRepository.findByName("Meta"));//使用自定义根据名称查询方法
//        log.info("{}",this.personRepository.findByNameLike("%t%"));//使用自定义模糊查询方法
//        Page<Person> personPage = this.personRepository.findAll(PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id")));//分页查询
//        log.info("{}", personPage);//自带无参分页查询
//        Page<Person> personPageByNameLike = this.personRepository.findAllByNameLike("%e%", PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id")));
//        personPageByNameLike.forEach(System.out::println);//自定义模糊分页查询（%M%这种写法是不会有SQL注入的，不信自己可以试试%M% and id = 1）
    }

    /**
     * 删除
     */
    @Test
    public void delete(){
        this.personRepository.deleteById(1L);//根据ID删除一条记录
        this.personRepository.deleteAllById(Arrays.asList(1L,2L,3L));//根据ID集合批量删除记录
    }

}
