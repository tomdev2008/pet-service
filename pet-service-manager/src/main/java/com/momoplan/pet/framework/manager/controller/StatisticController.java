package com.momoplan.pet.framework.manager.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.momoplan.pet.commons.domain.manager.po.MgrTrustUser;
import com.momoplan.pet.framework.manager.service.StatisticService;
import com.momoplan.pet.framework.manager.vo.PageBean;
import com.momoplan.pet.framework.manager.vo.UsersStatisticVo;

@Controller
public class StatisticController {
	
	private Logger logger = LoggerFactory.getLogger(StatisticController.class);
	
	@Resource
	StatisticService statisticService = null;
	
	/**
	 * 调用service获取统计后的信息
	 * @param pageBean
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/manager/statistic/statisticUser.html")
	public String statisticUser(PageBean<MgrTrustUser> pageBean,Model model,HttpServletRequest request){
		logger.debug("wlcome to manager trustuser userList......");
		try {
			PageBean<UsersStatisticVo> bean = statisticService.getUsersData();
			model.addAttribute("pageBean", bean);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return "/manager/statistic/statisticUserList";
	}
	
	/**
	 * 调用service获取统计后的信息
	 * @param pageBean
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/manager/statistic/statisticMethod.html")
	public String statisticMethod(PageBean<MgrTrustUser> pageBean,Model model,HttpServletRequest request){
		logger.debug("wlcome to manager trustuser userList......");
		try {
//			PageBean<T> bean= service.dosomething()
//			model.addAttribute("pageBean", bean);
//			List<BizDailyMethod> bdms = statisticService.getDailyMethod();
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return "/manager/statistic/statisticMethodList";
	}

}
