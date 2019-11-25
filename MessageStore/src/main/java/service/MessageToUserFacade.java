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

    public MessageToUserFacade() {
        super(MessageToUser.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<MessageToUser> getAllUserMessages(String uid){
        //"SELECT * FROM message INNER JOIN message_to_user ON message.id=message_to_user.id INNER JOIN system_user ON message_to_user.user_uid=system_user.uid;"
        
        CriteriaBuilder cb = em.getCriteriaBuilder();
        
        CriteriaQuery<MessageToUser> cq = cb.createQuery(MessageToUser.class);

        Root<MessageToUser> root = cq.from(MessageToUser.class); //reverse this so root message?
        //Root<Message> root2 = cq.from(Message.class)
        Join<MessageToUser, Message> join = root.join("messageId");
        
        cq.select(root);
        cq.where(
            cb.equal(root.get("userUid"), uid),
            cb.equal(root.get("isOwner"), true)
        );
        
        TypedQuery<MessageToUser> q = em.createQuery(cq);
        List<MessageToUser> allMessages = q.getResultList();
        
        return allMessages;
    }
    
        /**
     * TODO
     * @return 
     */
//    public List<Message> getAllMessages(){
//        CriteriaBuilder cb = em.getCriteriaBuilder();        
//        CriteriaQuery<Message> cq = cb.createQuery(Message.class);
//        Root<Message> root = cq.from(Message.class); 
//        Join<Message, MessageToUser> join = root.join("messageToUserCollection");
//        
//        cq.select(cb.array(root.get("name"), root.get("userUid")));
//        
//        TypedQuery<Message> q = em.createQuery(cq);
//        List<Message> allMessages = q.getResultList();        
//        return allMessages;
//    } 
    
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
        return null; //TODO
    }
    
    /**
     * TODO
     * @return 
     */
    public List<Message> getMessagesForMentionEmail(String uid){       
        return null; //TODO
    }
    
}
