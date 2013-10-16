package com.momoplan.pet.framework.bbs.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.momoplan.pet.commons.PetUtil;
import com.momoplan.pet.commons.handler.RequestHandler;
import com.momoplan.pet.commons.spring.Bootstrap;
import com.momoplan.pet.framework.bbs.service.BbsNoteCountService;
import com.momoplan.pet.framework.bbs.service.ForumService;
import com.momoplan.pet.framework.bbs.service.NoteService;
import com.momoplan.pet.framework.bbs.service.NoteSubService;
import com.momoplan.pet.framework.bbs.service.UserForumRelService;
import com.momoplan.pet.framework.bbs.service.impl.BbsNoteCountServiceImpl;
import com.momoplan.pet.framework.bbs.service.impl.ForumServiceImpl;
import com.momoplan.pet.framework.bbs.service.impl.NoteServiceImpl;
import com.momoplan.pet.framework.bbs.service.impl.NoteSubServiceImpl;
import com.momoplan.pet.framework.bbs.service.impl.UserForumRelServiceImpl;

public abstract class AbstractHandler extends PetUtil implements RequestHandler {
	
	protected ForumService  forumService = Bootstrap.getBean(ForumServiceImpl.class);
	protected NoteService noteService= Bootstrap.getBean(NoteServiceImpl.class);
	protected NoteSubService noteSubService=Bootstrap.getBean(NoteSubServiceImpl.class);
	protected UserForumRelService userForumRelService=Bootstrap.getBean(UserForumRelServiceImpl.class);
	protected BbsNoteCountService bbsNoteCountService=Bootstrap.getBean(BbsNoteCountServiceImpl.class);
	protected Gson gson = new Gson();
	
	private Logger logger = LoggerFactory.getLogger(AbstractHandler.class);
}