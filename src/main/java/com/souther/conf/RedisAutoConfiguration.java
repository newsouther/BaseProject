package com.souther.conf;

import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Auther: souther
 * @Date: 2020/7/1 00:11
 * @Description:
 */
@Configuration
@ConditionalOnClass({JedisCluster.class})
public class RedisAutoConfiguration {

  @Value(value = "${spring.redis.host}")
  private String host;
  @Value("${spring.redis.password}")
  private String password;
  @Value("${spring.redis.timeout}")
  private int connectionTimeout;
  /*  @Value("${redis.so_timeout:2000}")
    private int soTimeout;
    @Value("${redis.max_attempts:10}")
    private int maxAttempts;*/
  @Value("${spring.redis.jedis.pool.max-active}")
  private int maxTotal;
  @Value("${spring.redis.jedis.pool.min-idle}")
  private int minIdle;
  @Value("${spring.redis.jedis.pool.max-idle}")
  private int maxIdle;
  @Value("${spring.redis.jedis.pool.max-wait}")
  private int maxWaitMillis;

  @Bean(name = "myRedisClusterConfiguration")
  public RedisClusterConfiguration jedisCluster() {
    RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
    String[] hosts = host.split(",");
    Set<RedisNode> nodeList = new HashSet<RedisNode>();
    for (String hostAndPort : hosts) {
      String[] hostOrPort = hostAndPort.split(":");
      nodeList.add(new RedisNode(hostOrPort[0], Integer.parseInt(hostOrPort[1])));
    }
    redisClusterConfiguration.setClusterNodes(nodeList);
    redisClusterConfiguration.setMaxRedirects(maxWaitMillis);
    return redisClusterConfiguration;
  }

  @Bean(name = "myJedisPoolConfig")
  public JedisPoolConfig jedisPoolConfig() {
    JedisPoolConfig poolConfig = new JedisPoolConfig();
    poolConfig.setMaxIdle(this.maxIdle);
    poolConfig.setMinIdle(this.minIdle);
    poolConfig.setTestOnCreate(true);
    poolConfig.setTestOnBorrow(true);
    poolConfig.setTestOnReturn(true);
    poolConfig.setTestWhileIdle(true);
    return poolConfig;
  }

  //  @Bean(name = "MyJedisConnectionFactory")
//  public JedisConnectionFactory getJedisConnectionFactory(@Qualifier("myRedisClusterConfiguration") RedisClusterConfiguration redisClusterConfiguration,
//      @Qualifier("myJedisPoolConfig") JedisPoolConfig jedisPoolConfig){
//    JedisConnectionFactory  jedisConnectionFactory  = new JedisConnectionFactory(redisClusterConfiguration, jedisPoolConfig);
//    // 判断密码是否存在，存在设置值
//    checkPasswordIfNull(jedisConnectionFactory);
//    return jedisConnectionFactory;
//  }
  @Bean(name = "MyJedisConnectionFactory")
  public JedisConnectionFactory getJedisConnectionFactory(
      RedisClusterConfiguration redisClusterConfiguration,
      @Qualifier("myJedisPoolConfig") JedisPoolConfig jedisPoolConfig) {
    JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(
        redisClusterConfiguration, jedisPoolConfig);
    // 判断密码是否存在，存在设置值
    checkPasswordIfNull(jedisConnectionFactory);
    return jedisConnectionFactory;
  }


  @Bean(name = "myRedisTemplates")
  public RedisTemplate redisTemplate(
      @Qualifier("MyJedisConnectionFactory") JedisConnectionFactory jedisConnectionFactory) {
    StringRedisTemplate myRedisTemplate = new StringRedisTemplate();
    myRedisTemplate.setConnectionFactory(jedisConnectionFactory);
    return myRedisTemplate;
  }

  private void checkPasswordIfNull(JedisConnectionFactory jedisConnectionFactory) {
    if (!StringUtils.isBlank(password)) {
      jedisConnectionFactory.setPassword(password);
    }
  }

}
