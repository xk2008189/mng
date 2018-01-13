package com.pro.common.shiro;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.pro.system.entity.User;
import com.pro.system.service.UserService;

public class ShrioRealm  extends AuthorizingRealm{
	 @Resource  
	 private UserService userService;  
	
	/**
	 * 认证回调函数, 登录时调用
	 * 
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		 System.out.println("登录时");
		 String username = (String) token.getPrincipal();
		 User user = userService.selectUserByName(username);  
		 if (user == null) {
            // 用户名不存在抛出异常
			throw new AuthenticationException("用户名或密码错误");
         }
		 if(username.endsWith(user.getUserName())){
			 SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getUserName(),
					 user.getPassword(), getName());
			 return authenticationInfo;
		 }else{
			 throw new AuthenticationException("用户名或密码错误");
		 }

	}

	

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("授权");
		return null;
	}
	
}
