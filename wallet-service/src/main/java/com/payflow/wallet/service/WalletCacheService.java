package com.payflow.wallet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class WalletCacheService {

    private final RedisTemplate<String , Object> redisTemplate;

    private String  getWalletKey(Long walletId){
        return "wallet:" +walletId;
    }

    public void saveBalance(Long walletId, BigDecimal balance){
        redisTemplate.opsForValue().set(
                getWalletKey(walletId),
                balance,
                Duration.ofMinutes(10)
        );
    }

    public BigDecimal getBalance(Long walletId){

        Object value = redisTemplate.opsForValue()
                .get(getWalletKey(walletId));

        if (value == null){
            return null;
        }

        return new BigDecimal(value.toString());
    }

    public void evictBalance(
            Long walletId
    ) {
        redisTemplate.delete(
                getWalletKey(walletId)
        );
    }
}
