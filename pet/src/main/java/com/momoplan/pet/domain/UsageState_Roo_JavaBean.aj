// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.momoplan.pet.domain;

import com.momoplan.pet.domain.UsageState;
import java.util.Date;

privileged aspect UsageState_Roo_JavaBean {
    
    public Date UsageState.getLastConnectTime() {
        return this.lastConnectTime;
    }
    
    public void UsageState.setLastConnectTime(Date lastConnectTime) {
        this.lastConnectTime = lastConnectTime;
    }
    
    public long UsageState.getUserId() {
        return this.userId;
    }
    
    public void UsageState.setUserId(long userId) {
        this.userId = userId;
    }
    
}