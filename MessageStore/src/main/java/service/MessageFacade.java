/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Message;
import entities.MessageToUser;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Morgan
 */
@Stateless
public class MessageFacade extends AbstractFacade<Message>{
    
    /**
     * Entity Manager to interact with persistence context (PostgreSQL Database).
     */
    @PersistenceContext(unitName = "com.mycompany_RestJpa_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public MessageFacade() {
        super(Message.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<Message> getMessagesById(String uid){
        CriteriaBuilder cb = em.getCriteriaBuilder();        
        CriteriaQuery<Message> cq = cb.createQuery(Message.class);
        Root<Message> root = cq.from(Message.class);
        
        cq.select(root);
        cq.where(cb.equal(root.get("ownerUid"), uid));
        
        TypedQuery<Message> q = em.createQuery(cq);
        List<Message> result = q.getResultList();
        
        return result;
    }
}
