package com.souther.conf;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import redis.clients.jedis.JedisCluster;

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

//  @Bean(name = "myRedisClusterConfiguration")
//  public RedisClusterConfiguration jedisCluster() {
//    RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
//    String[] hosts = host.split(",");
//    Set<RedisNode> nodeList = new HashSet<RedisNode>();
//    for (String hostAndPort : hosts) {
//      String[] hostOrPort = hostAndPort.split(":");
//      nodeList.add(new RedisNode(hostOrPort[0], Integer.parseInt(hostOrPort[1])));
//    }
//    redisClusterConfiguration.setClusterNodes(nodeList);
//    redisClusterConfiguration.setMaxRedirects(maxWaitMillis);
//    return redisClusterConfiguration;
//  }
//
//  @Bean(name = "myJedisPoolConfig")
//  public JedisPoolConfig jedisPoolConfig() {
//    JedisPoolConfig poolConfig = new JedisPoolConfig();
//    poolConfig.setMaxIdle(this.maxIdle);
//    poolConfig.setMinIdle(this.minIdle);
//    poolConfig.setTestOnCreate(true);
//    poolConfig.setTestOnBorrow(true);
//    poolConfig.setTestOnReturn(true);
//    poolConfig.setTestWhileIdle(true);
//    return poolConfig;
//  }
//
//  @Bean(name = "MyJedisConnectionFactory")
//  public JedisConnectionFactory getJedisConnectionFactory(
//      @Qualifier("myRedisClusterConfiguration") RedisClusterConfiguration redisClusterConfiguration,
//      @Qualifier("myJedisPoolConfig") JedisPoolConfig jedisPoolConfig) {
//    JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(
//        redisClusterConfiguration, jedisPoolConfig);
//    // 判断密码是否存在，存在设置值
//    checkPasswordIfNull(jedisConnectionFactory);
//    return jedisConnectionFactory;
//  }
//
//
//  @Bean(name = "myRedisTemplates")
//  public RedisTemplate redisTemplate(
//      @Qualifier("MyJedisConnectionFactory") JedisConnectionFactory jedisConnectionFactory) {
//    StringRedisTemplate myRedisTemplate = new StringRedisTemplate();
//    myRedisTemplate.setConnectionFactory(jedisConnectionFactory);
//    return myRedisTemplate;
//  }
//
//  private void checkPasswordIfNull(JedisConnectionFactory jedisConnectionFactory) {
//    if (!StringUtils.isBlank(password)) {
//      jedisConnectionFactory.setPassword(password);
//    }
//  }

  @Bean(name = "myRedisTemplates")
  public RedisTemplate<String, Object> redisTemplate(
      RedisConnectionFactory redisConnectionFactory) {
    Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer =
        new Jackson2JsonRedisSerializer<Object>(Object.class);
    ObjectMapper om = new ObjectMapper();
    om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    jackson2JsonRedisSerializer.setObjectMapper(om);
    RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
    template.setConnectionFactory(redisConnectionFactory);
    template.setKeySerializer(jackson2JsonRedisSerializer);
    template.setValueSerializer(jackson2JsonRedisSerializer);
    template.setHashKeySerializer(jackson2JsonRedisSerializer);
    template.setHashValueSerializer(jackson2JsonRedisSerializer);
    template.afterPropertiesSet();
    return template;
  }

}
