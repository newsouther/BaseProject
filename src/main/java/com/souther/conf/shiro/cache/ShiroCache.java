package com.souther.conf.shiro.cache;

import com.alibaba.fastjson.JSONObject;
import com.souther.common.constant.RedisKeyEnum;
import com.souther.utils.JwtUtil;
import com.souther.utils.RedisUtil;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

/**
 * @Auther: souther
 * @Date: 2020/6/30 14:19
 * @Description: 重写Shiro的Cache保存读取
 */
public class ShiroCache<K, V> implements Cache<K, V> {

//  @Autowired  这个是分布式配置啦
//  private AuthProperties authProperties;

  //  @Value("${shiro.cache.expire}")
  private Long shiroCacheExpireTime = 7200L;

  /**
   * 缓存的key名称获取为shiro:cache:account
   *
   * @param key
   * @return java.lang.String
   */
  private String getKey(Object key) {
    return String
        .format(RedisKeyEnum.SHIRO_CACHE.getKey(), JwtUtil.getUserIdByToken(key.toString()));
  }

  /**
   * 获取缓存
   */
  @Override
  public Object get(Object key) throws CacheException {
    if (Boolean.FALSE.equals(RedisUtil.hasKey(this.getKey(key)))) {
      return null;
    }
    String jsonString = RedisUtil.get(this.getKey(key));
    Object parseObject = JSONObject.parseObject(jsonString, SimpleAuthorizationInfo.class);
    return parseObject;
  }

  /**
   * 保存缓存
   */
  @Override
  public Object put(Object key, Object value) throws CacheException {
    // 设置Redis的Shiro缓存
    String jsonString = JSONObject.toJSONString(value);
    RedisUtil.setEx(this.getKey(key), jsonString, shiroCacheExpireTime, TimeUnit.SECONDS);
    return value;
  }

  /**
   * 移除缓存
   */
  @Override
  public Object remove(Object key) throws CacheException {
    if (Boolean.FALSE.equals(RedisUtil.hasKey(this.getKey(key)))) {
      return null;
    }
    RedisUtil.delete(this.getKey(key));
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
