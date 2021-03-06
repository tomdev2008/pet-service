package com.momoplan.pet.framework.ssoserver.handler.impl;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.commons.domain.user.dto.SsoAuthenticationToken;
import com.momoplan.pet.commons.domain.user.po.SsoUser;
import com.momoplan.pet.framework.ssoserver.handler.AbstractHandler;

/**
 * 注册
 * @author liangc
 */
@Component("register")
public class RegisterHandler extends AbstractHandler {
	
	private Logger logger = LoggerFactory.getLogger(RegisterHandler.class);
	
	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		String sn = clientRequest.getSn();
		try{
			SsoUser user = reviceSsoUser(clientRequest);
			SsoAuthenticationToken token = ssoService.register(user);
			logger.debug("注册成功 body="+gson.toJson(clientRequest));
			rtn = new Success(sn,true,token).toString();
		}catch(Exception e){
			logger.debug("注册失败 body="+gson.toJson(clientRequest));
			logger.error("register : ",e);
			rtn = new Success(sn,false,e.getMessage()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}

}
