package com.momoplan.pet.framework.ssoserver.handler;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;

import com.google.gson.Gson;
import com.momoplan.pet.commons.MyGson;
import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.bean.ClientRequest;
import com.momoplan.pet.commons.domain.user.po.SsoUser;
import com.momoplan.pet.commons.handler.RequestHandler;
import com.momoplan.pet.commons.spring.Bootstrap;
import com.momoplan.pet.commons.web.BaseController;
import com.momoplan.pet.framework.ssoserver.service.SsoService;
import com.momoplan.pet.framework.ssoserver.service.impl.SsoServiceImpl;

public abstract class AbstractHandler extends BaseController implements RequestHandler {
	
	protected SsoService ssoService = Bootstrap.getBean(SsoServiceImpl.class);
	protected Gson gson = MyGson.getInstance();
	
	private Logger logger = LoggerFactory.getLogger(AbstractHandler.class);
	
	protected final static String salt = "$salt";
	protected static PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
	
	protected SsoUser reviceSsoUser(ClientRequest clientRequest){
		String password = PetUtil.getParameter(clientRequest, "password");
		String phoneNumber = PetUtil.getParameter(clientRequest, "phonenumber");
		String username = PetUtil.getParameter(clientRequest, "username");
		String src = PetUtil.getParameter(clientRequest, "src");
		String nickname = PetUtil.getParameter(clientRequest, "nickname");
		String birthdate = PetUtil.getParameter(clientRequest, "birthdate");
		String gender = PetUtil.getParameter(clientRequest, "gender");
		String city = PetUtil.getParameter(clientRequest, "city");
		String signature = PetUtil.getParameter(clientRequest, "signature");
		String img = PetUtil.getParameter(clientRequest, "img");
		String hobby = PetUtil.getParameter(clientRequest, "hobby");
		String deviceToken = PetUtil.getParameter(clientRequest, "deviceToken");
		if(StringUtils.isEmpty(username)){
			username = phoneNumber;
		}
		SsoUser petUser = new SsoUser();
		petUser.setUsername(username);// 将手机号作为用户名
		petUser.setSignature(signature);
		// petUser.setEmail(email);
		petUser.setBirthdate(birthdate);
		petUser.setGender(gender);
		petUser.setCity(city);
		petUser.setNickname(nickname);
		petUser.setPhoneNumber(phoneNumber);
		petUser.setHobby(hobby);
		petUser.setImg(img);
		petUser.setSrc(src);
		petUser.setIfFraudulent("0");
		petUser.setDeviceToken(deviceToken);
		petUser.setPassword(passwordEncoder.encodePassword(password,salt));
		petUser.setCreateTime(new Date(System.currentTimeMillis()));
		logger.debug("reviceSsoUser : "+petUser.toString());
		return petUser;
	}
}
