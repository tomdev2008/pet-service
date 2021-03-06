package com.momoplan.pet.framework.ssoserver.handler.impl;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.ShardedJedis;

import com.momoplan.pet.commons.NumberUtils;
import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.commons.cache.pool.RedisPool;
import com.momoplan.pet.framework.ssoserver.CacheKeysConstance;
import com.momoplan.pet.framework.ssoserver.handler.AbstractHandler;

/**
 * 获取验证码
 * @author liangc
 * body={"method":"getVerificationCode","params":{"phoneNum":"15199999999"}}
 */
@Component("getVerificationCode")
public class GetXcodeHandler extends AbstractHandler implements CacheKeysConstance{
	
	private Logger logger = LoggerFactory.getLogger(GetXcodeHandler.class);
	@Autowired
	private RedisPool redisPool = null;
	private int ex = 60*10;
	
	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		String sn = clientRequest.getSn();
		try{
			String phoneNumber = PetUtil.getParameter(clientRequest, "phoneNum");
//			String xcode = NumberUtils.getIdentifyingCode();//字母加数字4位
			String xcode = NumberUtils.getIdentifyingCodeNum();//纯数字4位
			saveXcode(phoneNumber,xcode);
			logger.debug("获取验证码 成功 body="+gson.toJson(clientRequest)+"; xcode="+xcode);
			rtn = new Success(sn,true,xcode).toString();
		}catch(Exception e){
			logger.debug("获取验证码 失败 body="+gson.toJson(clientRequest));
			logger.error("login : ",e);
			rtn = new Success(sn,false,e.getMessage()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}

	private void saveXcode(String phoneNumber,String xcode)throws Exception{
		ShardedJedis jedis = null;
		String key = CF_XCODE+phoneNumber;
		try{
			jedis = redisPool.getConn();
			jedis.setex(key, ex, xcode);
			logger.debug(key+" = "+xcode);
		}catch(Exception e){
			logger.error("",e);
			throw e;
		}finally{
			redisPool.closeConn(jedis);
		}
	}
}
