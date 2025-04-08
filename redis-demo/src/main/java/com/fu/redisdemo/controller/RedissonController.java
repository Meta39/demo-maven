package com.fu.redisdemo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/redisson")
@RestController
@RequiredArgsConstructor
public class RedissonController {
    private final RedissonClient redisson;
    private int i = 10;

    /**
     * 不加锁，多线程并发操作减扣商品引出超卖问题
     */
    @PostMapping("/subProductNoLock")
    public void subProductNoLock() {
        if (i == 0) {
            log.info("卖完了~");
            return;
        }
        i--;
        log.info("您抢到了第" + i + "个免单券！");
    }

    /**
     * 加分布式锁，解决多线程并发操作减扣商品超卖问题
     */
    @PostMapping("/subProductLock")
    public void subProduct() {
        RLock lock = redisson.getLock("lock");
        boolean tryLock = lock.tryLock();
        if (tryLock) {
            try {
                if (i == 0) {
                    log.info("卖完了~");
                }
                i--;
                log.info("您抢到了第" + i + "个免单券！");
                return;
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                throw new RuntimeException(e.getMessage());
            } finally {
                lock.unlock();
            }
        }
        log.info("哎呀~活动太火爆了~请稍后再试");
    }

    /**
     * 重置库存
     */
    @GetMapping("/reset")
    public void reset() {
        i = 10;
    }

}
