package com.momoplan.pet.framework.ssoserver.service;

import com.momoplan.pet.commons.domain.user.dto.SsoAuthenticationToken;
import com.momoplan.pet.commons.domain.user.po.SsoChatServer;
import com.momoplan.pet.commons.domain.user.po.SsoUser;
import com.momoplan.pet.commons.domain.user.po.SsoVersion;
import com.momoplan.pet.framework.ssoserver.CacheKeysConstance;
import com.momoplan.pet.framework.ssoserver.vo.LoginResponse;

/**
 * 注册、登录、重设密码、注销登录、token校验
 * @author liangc
 */
public interface SsoService extends CacheKeysConstance{
	
	/**
	 * 获取聊天服务器
	 * TODO: 获取服务器集群中最空闲的一个
	 * @return
	 * @throws Exception
	 */
	public SsoChatServer getSsoChatServer()throws Exception;
	/**
	 * 新用户注册
	 * @param clientRequest
	 * @return 返回 token 的 json 字符串
	 * @throws Exception
	 */
	public SsoAuthenticationToken register(SsoUser user) throws Exception ;
	/**
	 * 登录
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public LoginResponse login(SsoUser user) throws Exception ;
	/**
	 * 退出登录
	 * @param token
	 * @throws Exception
	 */
	public void logout(String token) throws Exception ;
	/**
	 * 校验TOKEN
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public LoginResponse getToken(String token) throws Exception ;
	/**
	 * 重设密码
	 * @param user
	 * @throws Exception
	 */
	public void updatePassword(SsoUser user) throws Exception ;
	/**
	 * 根据用户名获取用户
	 * @param username
	 * @return
	 */
	public SsoUser getSsoUserByName(String username)throws Exception ;
	/**
	 * 获取当前版本信息
	 * @return
	 */
	public SsoVersion getVersion(String phoneType)throws Exception ;
	/**
	 * 获取开机图片
	 * @return
	 */
	public String getFirstImage()throws Exception ;

}
