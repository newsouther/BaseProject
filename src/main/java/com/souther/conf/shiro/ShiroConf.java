package com.souther.conf.shiro;

import com.souther.conf.shiro.cache.ShiroCacheManager;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.Filter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * @author Jirath
 * @date 2020/4/9
 * @description:
 */
@Configuration
@Slf4j
public class ShiroConf {

  /**
   * <h1>FactoryBean</h1>
   * FactoryBean to be used in Spring-based web applications for defining the master Shiro Filter.
   * <h1>factoryBean.setFilters</h1>
   * <p>Sets the filterName-to-Filter map of filters available for reference when creating filter
   * chain definitions. Note: This property is optional: this FactoryBean implementation will
   * discover all beans in the web application context that implement the Filter interface and
   * automatically add them to this filter map under their bean name.
   * </p>
   * <code>
   * Map<String, Filter> filterMap = new HashMap<>(); filterMap.put("jwt", new JwtFilter());
   * factoryBean.setFilters(filterMap);
   * </code></br>
   * <p><b>上述代码的目的是生成自定义的filter用来过滤请求</b></p>
   *
   * @param securityManager
   * @return
   */
  @Bean
  public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
    ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
    shiroFilterFactoryBean.setSecurityManager(securityManager);
    //注册拦截方案
    Map<String, Filter> filterMap = new HashMap<>();
    filterMap.put("jwt", new JwtShiroFilter());
    shiroFilterFactoryBean.setFilters(filterMap);
    //定义拦截规则
    Map<String, String> filterRuleMap = new HashMap<>();
    //登陆相关api不需要被过滤器拦截
//    filterRuleMap.put("/api/wx/user/login/**", "anon");
//    filterRuleMap.put("/api/response/**", "anon");
//    swagger
    filterRuleMap.put("/swagger-resources/**", "anon");
    filterRuleMap.put("/api-docs", "anon");
    filterRuleMap.put("/doc.html", "anon");
    filterRuleMap.put("/api-docs-ext", "anon");
    filterRuleMap.put("/webjars/**", "anon");
    filterRuleMap.put("/v2/**", "anon");
    filterRuleMap.put("/swagger-ui.html", "anon");

    //登录
    filterRuleMap.put("/api/open/**", "anon");
    // 所有请求通过JWT Filter
    filterRuleMap.put("/**", "jwt");
    shiroFilterFactoryBean.setFilterChainDefinitionMap(filterRuleMap);
    return shiroFilterFactoryBean;
  }

//  @Bean
//  public JwtShiroFilter jwtShiroFilter() {
//    JwtShiroFilter jwtShiroFilter = new JwtShiroFilter();
//    return jwtShiroFilter;
//  }

  /**
   * 因为本项目只用了一个Realm，所以使用了构造器进行初始化，该构造器只适合单Realm的情况
   *
   * @param tokenRealm
   * @return
   */
  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Bean("securityManager")
  public DefaultWebSecurityManager securityManager(TokenRealm tokenRealm,
      ShiroCacheManager shiroCacheManager) {
    log.info("注入Shiro的Web过滤器-->securityManager");
    DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager(tokenRealm);
    //设置realm
    defaultWebSecurityManager.setRealm(tokenRealm);
    //关闭session
    DefaultSubjectDAO subjectDAO = (DefaultSubjectDAO) defaultWebSecurityManager.getSubjectDAO();
    DefaultSessionStorageEvaluator evaluator = (DefaultSessionStorageEvaluator) subjectDAO
        .getSessionStorageEvaluator();
    evaluator.setSessionStorageEnabled(Boolean.FALSE);
    subjectDAO.setSessionStorageEvaluator(evaluator);
    // 设置自定义Cache缓存
    defaultWebSecurityManager.setCacheManager(shiroCacheManager);

    return defaultWebSecurityManager;
  }

/**
 * ============================= Shiro注解设置  ===============================================
 */

  /**
   * Shiro生命周期处理器
   */
  @Bean(name = "lifecycleBeanPostProcessor")
  public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
    return new LifecycleBeanPostProcessor();
  }

  /**
   * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
   */
  @Bean
  @DependsOn("lifecycleBeanPostProcessor")
  public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
    DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
    creator.setProxyTargetClass(true);
    return creator;
  }

  /**
   * 开启aop注解支持
   *
   * @param securityManager
   * @return
   */
  @Bean
  public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
      DefaultWebSecurityManager securityManager) {
    AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
    authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
    return authorizationAttributeSourceAdvisor;
  }
}
