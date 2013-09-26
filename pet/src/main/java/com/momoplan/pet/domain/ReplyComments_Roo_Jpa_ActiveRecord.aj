// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.momoplan.pet.domain;

import com.momoplan.pet.domain.ReplyComments;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect ReplyComments_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager ReplyComments.entityManager;
    
    public static final EntityManager ReplyComments.entityManager() {
        EntityManager em = new ReplyComments().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long ReplyComments.countReplyCommentses() {
        return entityManager().createQuery("SELECT COUNT(o) FROM ReplyComments o", Long.class).getSingleResult();
    }
    
    public static List<ReplyComments> ReplyComments.findAllReplyCommentses() {
        return entityManager().createQuery("SELECT o FROM ReplyComments o", ReplyComments.class).getResultList();
    }
    
    public static ReplyComments ReplyComments.findReplyComments(Long id) {
        if (id == null) return null;
        return entityManager().find(ReplyComments.class, id);
    }
    
    public static List<ReplyComments> ReplyComments.findReplyCommentsEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM ReplyComments o", ReplyComments.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void ReplyComments.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void ReplyComments.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            ReplyComments attached = ReplyComments.findReplyComments(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void ReplyComments.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void ReplyComments.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public ReplyComments ReplyComments.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        ReplyComments merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}