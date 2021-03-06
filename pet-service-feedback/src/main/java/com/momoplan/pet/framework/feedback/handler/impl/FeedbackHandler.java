package com.momoplan.pet.framework.feedback.handler.impl;


import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.framework.feedback.handler.AbstractHandler;

@Component("feedback")
public class FeedbackHandler extends AbstractHandler {
	
	private static Logger logger = LoggerFactory.getLogger(FeedbackHandler.class);
	
	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		String sn = clientRequest.getSn();
		try{
			String userid = getUseridFParamSToken(clientRequest);
			String feedback = PetUtil.getParameter(clientRequest, "feedback");
			String email = PetUtil.getParameter(clientRequest, "email");

			feedbackService.feedback(feedback,email,userid);
			rtn = new Success(sn,true,"OK").toString();
			logger.debug("反馈成功 body="+gson.toJson(clientRequest));
		}catch(Exception e){
			logger.debug("反馈失败 body="+gson.toJson(clientRequest));
			logger.error(e.getMessage(),e);
			rtn = new Success(sn,false,e.getMessage()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}

}
