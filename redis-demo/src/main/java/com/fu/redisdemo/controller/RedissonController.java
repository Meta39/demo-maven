package com.fu.redisdemo.controller;

import com.fu.redisdemo.util.RedissonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        log.info("您抢到了第" + i + "个免单券！");
        i--;
    }

    /**
     * 加分布式锁，解决多线程并发操作减扣商品超卖问题
     */
    @PostMapping("/subProductLock")
    public void subProductLock() {
        boolean autoLock = RedissonUtils.autoLock(redisson, "subProductLock", () -> {
            if (i == 0) {
                log.info("卖完了~");
                return;
            }
            log.info("您抢到了第" + i + "个免单券！");
            i--;
        });
        if (!autoLock) {
            log.info("哎呀~活动太火爆了~请稍后再试");
        }
    }

    /**
     * 重置库存
     */
    @GetMapping("/reset")
    public void reset() {
        i = 10;
    }

}
