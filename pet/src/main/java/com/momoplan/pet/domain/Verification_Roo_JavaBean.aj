// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.momoplan.pet.domain;

import com.momoplan.pet.domain.Verification;

privileged aspect Verification_Roo_JavaBean {
    
    public String Verification.getPhoneNum() {
        return this.phoneNum;
    }
    
    public void Verification.setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
    
    public String Verification.getVerificationCode() {
        return this.verificationCode;
    }
    
    public void Verification.setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
    
}