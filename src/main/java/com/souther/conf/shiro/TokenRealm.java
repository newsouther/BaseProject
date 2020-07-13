package com.souther.conf.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.souther.entity.TbPermission;
import com.souther.entity.TbRole;
import com.souther.entity.TbRolePermission;
import com.souther.entity.TbUserRole;
import com.souther.service.TbPermissionService;
import com.souther.service.TbRolePermissionService;
import com.souther.service.TbRoleService;
import com.souther.service.TbUserRoleService;
import com.souther.utils.JwtUtil;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Jirath
 * @date 2020/4/9
 * @description: 继承AuthorizingRealm作为Realm使用，
 */
@Service
public class TokenRealm extends AuthorizingRealm {

  @Autowired
  private TbUserRoleService tbUserRoleService;

  @Autowired
  private TbRoleService tbRoleService;

  @Autowired
  private TbRolePermissionService tbRolePermissionService;

  @Autowired
  private TbPermissionService tbPermissionService;

  /**
   * 该方法是为了判断这个主体能否被本Realm处理，判断的方法是查看token是否为同一个类型
   *
   * @param authenticationToken
   * @return
   */
  @Override
  public boolean supports(AuthenticationToken authenticationToken) {
    return authenticationToken instanceof JwtShiroToken;
  }


  /**
   * 在需要验证身份进行登录时，会通过这个接口，调用本方法进行审核，将身份信息返回，有误则抛出异常，在外层拦截
   *
   * @param authenticationToken 这里收到的是自定义的token类型，在JwtShiroToken中，自动向上转型。得到的getCredentials为String类型，可以使用toString
   * @return
   * @throws AuthenticationException token异常，可以细化设置
   */
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {
    String submittedToken = authenticationToken.getCredentials().toString();
    //解析出信息
    Integer userId = JwtUtil.getUserIdByToken(submittedToken);
    //对信息进行辨别
    if (userId == null) {
      throw new AuthenticationException("please check your token");
    }
    boolean verifyToken = JwtUtil.verifyToken(submittedToken);
    if (!verifyToken) {
      throw new AuthenticationException("please check your token");
    }
    return new SimpleAuthenticationInfo(submittedToken, submittedToken, getName());
  }

  /**
   * 这个方法是用来添加身份信息的
   *
   * @param principalCollection
   * @return
   */
  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
    SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
    Integer userId = JwtUtil.getUserIdByToken(principalCollection.toString());
    //用户角色
    List<TbUserRole> dbUserRoles = tbUserRoleService
        .list(
            new QueryWrapper<TbUserRole>().select("role_id").eq(userId != null, "user_id", userId));
    if (dbUserRoles == null || dbUserRoles.isEmpty()) {
      return simpleAuthorizationInfo;
    }
    //角色id
    List<Integer> roleIdList = dbUserRoles.stream().map(u -> u.getRoleId())
        .collect(Collectors.toList());
    //角色
    List<TbRole> tbRoles = tbRoleService.listByIds(roleIdList);
    //角色权限
    List<TbRolePermission> dbRolePermission = tbRolePermissionService.list(
        new QueryWrapper<TbRolePermission>().select("permission_id")
            .in(roleIdList != null && !roleIdList.isEmpty(), "role_id", roleIdList));
    //权限id
    List<Integer> permissionIds = dbRolePermission.stream().map(u -> u.getPermissionId())
        .collect(Collectors.toList());
    //权限
    List<TbPermission> tbPermissions = tbPermissionService.listByIds(permissionIds);
    // 添加角色
    for (TbRole role :
        tbRoles) {
      simpleAuthorizationInfo.addRole(role.getName());
    }
    // 添加权限
    for (TbPermission permission :
        tbPermissions) {
      simpleAuthorizationInfo.addStringPermission(permission.getPerCode());
    }

    return simpleAuthorizationInfo;
  }

  /**
   * 注意坑点 : 密码校验 , 这里因为是JWT形式,就无需密码校验和加密,直接让其返回为true(如果不设置的话,该值默认为false,即始终验证不通过)
   */
  @Override
  public CredentialsMatcher getCredentialsMatcher() {
    return (token, info) -> true;
  }
}
