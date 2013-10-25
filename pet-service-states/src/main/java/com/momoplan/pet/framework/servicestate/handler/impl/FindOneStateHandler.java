package com.momoplan.pet.framework.servicestate.handler.impl;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.commons.domain.user.dto.SsoAuthenticationToken;
import com.momoplan.pet.framework.servicestate.handler.AbstractHandler;
import com.momoplan.pet.framework.servicestate.vo.StateResponse;

@Component("findOneState")
public class FindOneStateHandler extends AbstractHandler{
	private Logger logger = LoggerFactory.getLogger(AddUserStateHandler.class);
	
	@Override
	public void process(ClientRequest clientRequest,HttpServletResponse response) throws Exception {
		String rtn = null;
		try{
			SsoAuthenticationToken authenticationToken = verifyToken(clientRequest);
			StateResponse stateResponse = stateService.findOneState(clientRequest,authenticationToken);
			rtn = new Success(true,stateResponse.getStateView()).toString();
		}catch(Exception e){
			logger.debug("获取一条动态失败 body="+gson.toJson(clientRequest));
			logger.error("findOneState : ",e);
			rtn = new Success(false,e.getMessage()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}
}