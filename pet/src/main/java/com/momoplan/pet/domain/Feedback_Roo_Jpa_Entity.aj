// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.momoplan.pet.domain;

import com.momoplan.pet.domain.Feedback;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

privileged aspect Feedback_Roo_Jpa_Entity {
    
    declare @type: Feedback: @Entity;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Feedback.id;
    
    @Version
    @Column(name = "version")
    private Integer Feedback.version;
    
    public Long Feedback.getId() {
        return this.id;
    }
    
    public void Feedback.setId(Long id) {
        this.id = id;
    }
    
    public Integer Feedback.getVersion() {
        return this.version;
    }
    
    public void Feedback.setVersion(Integer version) {
        this.version = version;
    }
    
}