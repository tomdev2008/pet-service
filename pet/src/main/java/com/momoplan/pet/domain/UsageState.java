package com.momoplan.pet.domain;

import java.util.Date;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaEntity
@RooJpaActiveRecord(finders = { "findUsageStatesByUserId" })
public class UsageState {

    private Date lastConnectTime;

    private long userId;
}