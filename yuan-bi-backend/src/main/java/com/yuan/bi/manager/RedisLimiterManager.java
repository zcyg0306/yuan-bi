package com.yuan.bi.manager;

import com.yuan.bi.common.ErrorCode;
import com.yuan.bi.exception.BusinessException;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Redis限流管理器
 * 专门提供 RedisLimiter 限流基础服务的（提供了通用的能力，放其他项目都能用）
 */
@Service
public class RedisLimiterManager {

    @Resource
    private RedissonClient redissonClient;

    /**
     * 限流操作
     *
     * @param key 区分不同的限流器，比如不同的用户 ID 应该分别统计
     */
    public void doRateLimit(String key) {
        // 1. 获取 Redisson 的限流器对象,每秒最多访问2次
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(key);
        // 2. 设置限流器的速率（每秒2个请求；连续的请求最多1个）
        // RateType.OVERALL：整体速率限制
        rateLimiter.trySetRate(RateType.OVERALL, 2, 1, RateIntervalUnit.SECONDS);
        // 每当一个操作来了后，请求一个
        boolean can0p = rateLimiter.tryAcquire();
        // 如果没有令牌，还想执行操作，就抛出异常
        if (!can0p) {
            throw new BusinessException(ErrorCode.TOO_MANY_REQUEST);
        }
    }
}
