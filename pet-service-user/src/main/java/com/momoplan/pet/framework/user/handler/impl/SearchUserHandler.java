package com.momoplan.pet.framework.user.handler.impl;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.bean.Success;
import com.momoplan.pet.framework.user.handler.AbstractHandler;
import com.momoplan.pet.framework.user.vo.UserVo;

/**
 * 搜索用户，单条件搜索
 * @author liangc
 */
@Component("searchUser")
public class SearchUserHandler extends AbstractHandler {
	
	private static Logger logger = LoggerFactory.getLogger(SearchUserHandler.class);
	private Gson gson = MyGson.getInstance();
	
	@Override
	public void process(ClientRequest clientRequest, HttpServletResponse response) throws Exception {
		String rtn = null;
		String sn = clientRequest.getSn();
		try{
			String userid = getUseridFParamSToken(clientRequest);
			String condition = getParameter(clientRequest, "condition");
			String conditionType = getParameter(clientRequest, "conditionType");
			List<UserVo> list = userService.searchUser(userid,condition,conditionType);
			rtn = new Success(sn,true,list).toString();
			logger.debug("搜索用户 成功 body="+gson.toJson(clientRequest));
		}catch(Exception e){
			e.printStackTrace();
			logger.debug("搜索用户 失败 body="+gson.toJson(clientRequest));
			logger.error(e.getMessage(),e);
			rtn = new Success(sn,false,e.getMessage()).toString();
		}finally{
			logger.debug(rtn);
			writeStringToResponse(rtn,response);
		}
	}
	
}
