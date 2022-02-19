package com.imooc.distributedlock;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @Author：guozhihao
 * @Date：2022/2/19 4:23 下午
 */
@Slf4j
@RestController
public class LockTestController {

    @Autowired
    private RedissonClient redissonClient;

    @RequestMapping(  "/lockTest")
    public void lockTest(){
        RLock lock = redissonClient.getLock("lock");
        try {
            if (lock.tryLock()){
                log.info("拿到锁了");
                lock.lock();//加锁
                //假设程序耗时3s：开始
                Thread.sleep(3000);
                //假设程序耗时3s：结束
                lock.unlock();//解锁
                log.info("释放锁了");
            }else {
                log.info("很抱歉，拿不到锁");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
