package com.fu.redisdemo.controller;

import com.fu.redisdemo.service.FlashSaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sale")
public class FlashSaleController {
    @Autowired
    private FlashSaleService flashSaleService;

    /**
     * 自定义分布式锁（推荐）
     * 要先执行测试类里的设置库存。再用JMeter模拟并发请求。
     */
    @GetMapping
    public String sale(){
        return flashSaleService.sale();
    }

    /**
     * Redisson分布式锁（个人感觉不是很好用，而且集群模式下不推荐，因为会抛出：远程主机强迫关闭了一个现有的连接异常。）
     * 要先执行测试类里的设置库存。再用JMeter模拟并发请求。
     */
    @GetMapping("saleByRedisson")
    public String saleByRedisson(){
        return flashSaleService.saleByRedisson();
    }

}
