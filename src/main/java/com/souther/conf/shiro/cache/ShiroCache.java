package com.souther.conf.shiro.cache;

import com.souther.common.constant.RedisKeyEnum;
import com.souther.utils.JwtUtil;
import com.souther.utils.RedisUtil;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Auther: souther
 * @Date: 2020/6/30 14:19
 * @Description: 重写Shiro的Cache保存读取
 */
@Component
public class ShiroCache<K,V> implements Cache<K,V> {

//  @Autowired  这个是分布式配置啦
//  private AuthProperties authProperties;

  @Value("${shiro.cache.expire}")
  private Long shiroCacheExpireTime;

  /**
   * 缓存的key名称获取为shiro:cache:account
   * @param key
   * @return java.lang.String
   */
  private String getKey(Object key) {
    return String.format(RedisKeyEnum.SHIRO_CACHE.getKey(),JwtUtil.getUserIdByToken(key.toString()));
  }

  /**
   * 获取缓存
   */
  @Override
  public Object get(Object key) throws CacheException {
    if(Boolean.FALSE.equals(RedisUtil.existAny(this.getKey(key)))){
      return null;
    }
    return RedisUtil.get(this.getKey(key));
  }

  /**
   * 保存缓存
   */
  @Override
  public Object put(Object key, Object value) throws CacheException {
    // 设置Redis的Shiro缓存
    RedisUtil.setExpire(this.getKey(key), value, shiroCacheExpireTime, TimeUnit.SECONDS);
    return value;
  }

  /**
   * 移除缓存
   */
  @Override
  public Object remove(Object key) throws CacheException {
    if(Boolean.FALSE.equals(RedisUtil.existAny(this.getKey(key)))){
      return null;
    }
    RedisUtil.del(this.getKey(key));
    return null;
  }

  @Override
  public void clear() throws CacheException {

  }

  @Override
  public int size() {
    return 0;
  }

  @Override
  public Set<K> keys() {
    return null;
  }

  @Override
  public Collection<V> values() {
    return null;
  }
}
