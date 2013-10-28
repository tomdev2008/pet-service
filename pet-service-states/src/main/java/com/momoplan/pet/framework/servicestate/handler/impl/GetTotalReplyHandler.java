package com.momoplan.pet.framework.servicestate.handler.impl;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.commons.domain.user.dto.SsoAuthenticationToken;
import com.momoplan.pet.framework.servicestate.handler.AbstractHandler;
import com.momoplan.pet.framework.servicestate.vo.StatesUserStatesReplyVo;
/**
 * 获取回复总数
 * @author liangc
 */
@Component("getTotalReply")
public class GetTotalReplyHandler extends AbstractHandler{
	private Logger logger = LoggerFactory.getLogger(AddUserStateHandler.class);
	
	@Override
	public void process(ClientRequest clientRequest,HttpServletResponse response) throws Exception {
		String rtn = null;
		try{
			int total = 0;
			SsoAuthenticationToken authenticationToken = verifyToken(clientRequest);
			String userid = authenticationToken.getUserid();
			String stateid = PetUtil.getParameter(clientRequest, "stateid");
			List<StatesUserStatesReplyVo> voList = stateService.getReplyByStateid(userid, stateid, Integer.MAX_VALUE,0);
			if(voList!=null&&voList.size()>0)
				total = voList.size();
			rtn = new Success(true,total).toString();
			logger.debug("总回复数 成功 body="+gson.toJson(clientRequest));
		}catch(Exception e){
			logger.debug("总回复数 失败 body="+gson.toJson(clientRequest));
			logger.error("addReply : ",e);
			rtn = new Success(false,e.getMessage()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}
}
