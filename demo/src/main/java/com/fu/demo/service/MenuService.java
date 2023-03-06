package com.fu.demo.service;

import com.fu.demo.entity.Menu;
import com.fu.demo.dao.MenuDao;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;

@Service
public class MenuService {
    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    @Resource
    private MenuDao menuDao;

    //根据ID查询
    public Menu select(Long id) {
        return menuDao.select(id);
    }

    //查询全部
    public List<Menu> selectAll() {
        return menuDao.selectAll();
    }

    //新增
    public Integer insert(Menu menu) {
        return menuDao.insert(menu);
    }

    //更新
    public Integer update(Menu menu) {
        return menuDao.update(menu);
    }

    //删除
    public Integer delete(Long id) {
        return menuDao.delete(id);
    }
    //获取菜单树
    public List<Menu> menuTree() {
        List<Menu> menus = menuDao.selectAll();//获取数据库全部菜单
        return menus.stream().filter(m -> m.getPId() == 0).peek((m) -> m.setChildList(getMenuChildren(m, menus))).collect(Collectors.toList());
    }

    //获取菜单子列表
    private List<Menu> getMenuChildren(Menu parent, List<Menu> menuDatas) {
        return menuDatas.stream().filter(m -> Objects.equals(m.getPId(), parent.getId())).peek((m) -> m.setChildList(getMenuChildren(m, menuDatas))).collect(Collectors.toList());
    }

    //递归获取菜单树
    public Set<Menu> menuTree2(){
        Set<Menu> tree = new HashSet<>();
        List<Menu> menus = menuDao.selectAll();
        redisTemplate.opsForValue().set("operate2s",menus);//把树结构存放到redis提升性能
        for (Menu menu:menus){
            if (menu.getPId()==0){
                tree.add(getMenuChildren2(menu));
            }
        }
        return tree;
    }

    //递归
    public Menu getMenuChildren2(Menu menu){
        Set<Menu> childMenus = new HashSet<>();//Set插入、删除快，List插入慢，删除慢，查询快
        List<Menu> operate2s = (List<Menu>) redisTemplate.opsForValue().get("operate2s");//用redis减少mysql查询次数，性能优化
        assert operate2s != null;
        for (Menu operate:operate2s){
            if (operate.getPId().equals(menu.getId())){
                childMenus.add(getMenuChildren2(operate));
            }
        }
        menu.setChildList2(childMenus);
        return menu;
    }
}

