// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.momoplan.pet.domain;

import com.momoplan.pet.domain.PetType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

privileged aspect PetType_Roo_Jpa_Entity {
    
    declare @type: PetType: @Entity;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long PetType.id;
    
    @Version
    @Column(name = "version")
    private Integer PetType.version;
    
    public Long PetType.getId() {
        return this.id;
    }
    
    public void PetType.setId(Long id) {
        this.id = id;
    }
    
    public Integer PetType.getVersion() {
        return this.version;
    }
    
    public void PetType.setVersion(Integer version) {
        this.version = version;
    }
    
}