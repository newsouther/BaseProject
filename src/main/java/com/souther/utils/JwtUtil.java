package com.souther.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.souther.common.constant.RedisKeyEnum;
import com.souther.entity.TbUser;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Jirath
 * @date 2020/4/9
 * @description: jwt工具，createToken会产生redis缓存，检测会核实缓存
 */
@Slf4j
public class JwtUtil {

  /**
   * JWT 自定义密钥 在配置文件进行配置
   */
  private static String secretKey = "VGiXGUJDQl0OjjKmvTH3ycbkyVSg5Sb3RyPy40DpN98aDGX3";

  /**
   * JWT 过期时间值 这里写死为和小程序时间一致 7200 秒，也就是两个小时
   */
  private static final int EXPIRE_TIME = 7200;

  /**
   * 根据微信用户登陆信息创建 token 注 : 这里的token会被缓存到redis中,用作为二次验证 redis里面缓存的时间应该和jwt token的过期时间设置相同
   *
   * @param useInfo 用户信息
   * @return 返回 jwt token
   */
  public static String createTokenByWxAccount(TbUser useInfo) {
    //JWT 随机ID,做为验证的key
//    String jwtId = UUID.randomUUID().toString();
    //1 . 加密算法进行签名得到token
    //生成签名
    Algorithm algorithm = Algorithm.HMAC256(secretKey);
    //生成token
    String token = JWT.create()
        .withClaim("wxOpenId", useInfo.getOpenid())
        .withClaim("user-id", useInfo.getId())
        .withClaim("sessionKey", useInfo.getOpenid())
//        .withClaim("jwt-id", jwtId)
        //JWT 配置过期时间的正确姿势，因为单位是毫秒，所以需要乘1000
        .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_TIME * 1000))
        .sign(algorithm);
    //2 . Redis缓存JWT, 注 : 请和JWT过期时间一致
    String cacheKey = String.format(RedisKeyEnum.JWT_SESSION.getKey(), useInfo.getId());
    RedisUtil.setExpire(cacheKey, token, EXPIRE_TIME, TimeUnit.SECONDS);
    return token;
  }

  /**
   * 校验token是否正确 1 . 根据token解密，解密出jwt-id , 先从redis中查找出redisToken，匹配是否相同 2 . 然后再对redisToken进行解密，解密成功则
   * 继续流程 和 进行token续期
   *
   * @param token 密钥
   * @return 返回是否校验通过
   */
  public static boolean verifyToken(String token) {
    try {
      //1 . 根据token解密，解密出jwt-id , 先从redis中查找出redisToken，匹配是否相同
      String cacheKey = String.format(RedisKeyEnum.JWT_SESSION.getKey(), getUserIdByToken(token));
      String redisToken = RedisUtil.getStr(cacheKey);
      if (!redisToken.equals(token)) {
        return false;
      }
      //2 . 得到算法相同的JWTVerifier
      Algorithm algorithm = Algorithm.HMAC256(secretKey);
      JWTVerifier verifier = JWT.require(algorithm)
          .withClaim("wxOpenId", getWxOpenIdByToken(redisToken))
          .withClaim("user-id", getUserIdByToken(token))
          .withClaim("sessionKey", getSessionKeyByToken(redisToken))
//          .withClaim("jwt-id", getJwtIdByToken(redisToken))
          //续期
          .acceptExpiresAt(System.currentTimeMillis() + EXPIRE_TIME * 1000)
          .build();
      //3 . 验证token
      verifier.verify(redisToken);
      //4 . Redis缓存JWT续期
      RedisUtil.setExpire(cacheKey, redisToken, EXPIRE_TIME, TimeUnit.SECONDS);
      return true;
    } catch (Exception e) { //捕捉到任何异常都视为校验失败
      log.error("【JwtUtil-verifyToken-90】", e);
      return false;
    }
  }

  /**
   * 根据Token获取wxOpenId(注意坑点 : 就算token不正确，也有可能解密出wxOpenId,同下)
   */
  public static String getWxOpenIdByToken(String token) {
    return JWT.decode(token).getClaim("wxOpenId").asString();
  }

  /**
   * 根据Token获取sessionKey
   */
  public static String getSessionKeyByToken(String token) {
    return JWT.decode(token).getClaim("sessionKey").asString();
  }

  /**
   * 根据Token 获取jwt-id
   */
//  public String getJwtIdByToken(String token) {
//    return JWT.decode(token).getClaim("jwt-id").asString();
//  }

  /**
   * 根据Token 获取user-id
   */
  public static Integer getUserIdByToken(String token) {
    return JWT.decode(token).getClaim("user-id").asInt();
  }

}
