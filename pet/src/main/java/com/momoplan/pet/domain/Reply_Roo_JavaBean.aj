// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.momoplan.pet.domain;

import com.momoplan.pet.domain.Reply;
import java.util.Date;

privileged aspect Reply_Roo_JavaBean {
    
    public long Reply.getUserStateid() {
        return this.userStateid;
    }
    
    public void Reply.setUserStateid(long userStateid) {
        this.userStateid = userStateid;
    }
    
    public String Reply.getMsg() {
        return this.msg;
    }
    
    public void Reply.setMsg(String msg) {
        this.msg = msg;
    }
    
    public Date Reply.getReplyTime() {
        return this.replyTime;
    }
    
    public void Reply.setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }
    
    public long Reply.getPetUserid() {
        return this.petUserid;
    }
    
    public void Reply.setPetUserid(long petUserid) {
        this.petUserid = petUserid;
    }
    
}