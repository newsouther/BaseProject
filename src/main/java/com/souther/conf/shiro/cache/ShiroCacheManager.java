package com.souther.conf.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.stereotype.Component;

/**
 * @Auther: souther
 * @Date: 2020/6/30 14:18
 * @Description:
 */
@Component
public class ShiroCacheManager implements CacheManager {
  @Override
  public <K, V> Cache<K, V> getCache(String s) throws CacheException {
    return new ShiroCache<K,V>();
  }
}