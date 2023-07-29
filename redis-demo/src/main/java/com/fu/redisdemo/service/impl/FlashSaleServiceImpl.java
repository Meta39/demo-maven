package com.fu.redisdemo.service.impl;

import com.fu.redisdemo.lock.DistributedLockFactory;
import com.fu.redisdemo.lock.DistributedType;
import com.fu.redisdemo.service.FlashSaleService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.Lock;

@Slf4j
@Service
public class FlashSaleServiceImpl implements FlashSaleService {
    public static final String INVENTORY = "inventory001";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private DistributedLockFactory distributedLockFactory;

    @Autowired
    private Redisson redisson;

    /**
     * 自定义分布式锁（推荐）
     */
    @Override
    public String sale() {
        String retMessage;
        //获取分布式锁工厂当中的Redis分布式锁
        Lock redisLock = distributedLockFactory.getDistributedLock(DistributedType.Redis);
        //加锁
        redisLock.lock();
        //抢锁成功的请求线程，进行正常的业务逻辑操作，减扣库存。
        try {
            //1. 查询库存信息
            Integer result = (Integer) redisTemplate.opsForValue().get(INVENTORY);
            //2. 判断库存是否充足
            int enough = result == null ? 0 : result;
            //3. 减扣库存，每次减少一个
            if (enough > 0) {
                redisTemplate.opsForValue().set(INVENTORY, --enough);
                retMessage = "成功卖出一个商品，库存剩余：" + enough;
//                Thread.sleep(35_000); //阻塞，测试业务超时，键续期是否成功。
            } else {
                retMessage = "商品卖完了~(ㄒoㄒ)";
            }
        } catch (Exception e) {
            log.error("FlashSaleServiceImpl抛出异常：", e);
            throw new RuntimeException(e.getMessage());
        } finally {
            //只能删除自己的key，不能删除别人的key。lua脚本保证原子性
            redisLock.unlock();
        }
        log.info("{}", retMessage);
        return retMessage;
    }

    /**
     * Redisson分布式锁（个人感觉不是很好用，而且集群模式下不推荐，因为会抛出：远程主机强迫关闭了一个现有的连接异常。）
     */
    @Override
    public String saleByRedisson() {
        String retMessage;
        //加Redisson分布式锁
        RLock redissonLock = redisson.getLock("redissonLock");
        redissonLock.lock();
        //抢锁成功的请求线程，进行正常的业务逻辑操作，减扣库存。
        try {
            //1. 查询库存信息
            Integer result = (Integer) redisTemplate.opsForValue().get(INVENTORY);
            //2. 判断库存是否充足
            int enough = result == null ? 0 : result;
            //3. 减扣库存，每次减少一个
            if (enough > 0) {
                redisTemplate.opsForValue().set(INVENTORY, --enough);
                retMessage = "成功卖出一个商品，库存剩余：" + enough;
//                Thread.sleep(35_000); //阻塞，测试业务超时，键续期是否成功。
            } else {
                retMessage = "商品卖完了~(ㄒoㄒ)";
            }
        } catch (Exception e) {
            log.error("FlashSaleServiceImpl抛出异常：", e);
            throw new RuntimeException(e.getMessage());
        } finally {
            //只能删除自己的key，不能删除别人的key。Redisson内部做了封装不用写lua脚本
            if (redissonLock.isLocked() && redissonLock.isHeldByCurrentThread()){
                redissonLock.unlock();
            }
        }
        log.info("{}", retMessage);
        return retMessage;
    }

}
