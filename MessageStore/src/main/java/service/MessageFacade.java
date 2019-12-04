/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Message;
import entities.MessageToUser;
import java.util.List;
import java.util.Vector;
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
    @PersistenceContext(unitName = "MessageStore-1.0-SNAPSHOT")
    private EntityManager em;

    public MessageFacade() {
        super(Message.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    /**
     * 
     * @param uid
     * @return 
     */
    public List<Message> getOwnedMessages(String uid){
        List<Message> result = new Vector<>();
        List<Message> messages = super.findAll();
        for(Message m : messages){
            try{
                if(m.getOwnerUid().equals(uid)){
                    result.add(m);
                }
            } catch(NullPointerException e){
            }
        }
        return result;
    }
   
}
