/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Message;
import entities.MessageToUser;
import java.util.Collection;
import java.util.List;
import java.util.Vector;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

/**
 *
 * @author Morgan
 */
@Stateless
public class MessageToUserFacade extends AbstractFacade<MessageToUser> {
    
    /**
     * Entity Manager to interact with persistence context (PostgreSQL Database).
     */
    @PersistenceContext(unitName = "com.mycompany_RestJpa_war_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    @EJB
    private MessageFacade messageFacade;

    public MessageToUserFacade() {
        super(MessageToUser.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

   
    
    /**
     * If a user has been previously notified about a given by email ?REVISE THIS?
     * @param uid
     * @param message
     * @return 
     */
    private boolean hasBeenNotified(String uid, Message message){
        List<MessageToUser> messageToUsers = super.findAll();
        for(MessageToUser tuple : messageToUsers){
            if(tuple.getUserUid().equals(uid) && tuple.getMessageId().equals(message) && !tuple.getHasBeenNotified()){
                return true;
            }
        }
        return false;
    }
    
    /**
     * TODO
     * @return 
     */
    public List<Message> getMessagesForDailyEmail(String uid){
        return null; //TODO
    }
    
    /**
     * TODO
     * @return 
     */
    public List<Message> getMessagesForReplyEmail(String uid){
        List<Message> result = new Vector<Message>();
        List<Message> ownedMessages = messageFacade.getOwnedMessages(uid);
        for(Message m : ownedMessages){
            Collection<Message> replies = m.getMessageCollection();
            for(Message reply : replies){
                if(hasBeenNotified(uid,reply)==false){
                    result.add(reply);
                    //TODO set has been notified to true
                }
            }
        }
        return result;
    }
    
    /**
     * TODO
     * @return 
     */
    public List<Message> getMessagesForMentionEmail(String uid){       
        return null; //TODO
    }
    
}
