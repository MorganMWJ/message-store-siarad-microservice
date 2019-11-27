/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Message;
import entities.MessageToUser;
import java.util.ArrayList;
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
     * Get all messages created today that a user has
     * some association with that but has not yet been notified of .
     * @return 
     */
    public List<Message> getMessagesForDailyEmail(String uid){
        List<Message> result = new Vector<Message>();
        
        List<MessageToUser> messageToUsers = super.findAll();
        for(MessageToUser association : messageToUsers){
            boolean isMessageCreatedToday = association.getMessageId().isCreatedToday();
            boolean isUserAssociation = association.getUserUid().equals(uid);
            boolean hasUserBeenNotified = association.getHasBeenNotified();
            if(isMessageCreatedToday && isUserAssociation && !hasUserBeenNotified){
                result.add(association.getMessageId());
                association.setHasBeenNotified(true);
                super.edit(association);
            }
        }       
        
        return result;
    }
    
    /**
     * Get all replies to all the messages the user owns that he/she has not yet been notified of.
     * @return 
     */
    public List<Message> getMessagesForReplyEmail(String uid){
        List<Message> result = new Vector<Message>();
        List<Message> ownedMessages = messageFacade.getOwnedMessages(uid);
        for(Message m : ownedMessages){
            Collection<Message> replies = m.getMessageCollection();
            for(Message reply : replies){
                try{
                    MessageToUser replyAssociation = getMessageAssociation(uid, reply);
                    if(!replyAssociation.getHasBeenNotified()){
                        result.add(reply);
                        replyAssociation.setHasBeenNotified(true); //NEED TO PERSIST
                        super.edit(replyAssociation);
                }
                }catch(NullPointerException e){
                        //Continue OK
                    }
                }
        }
        return result;
    }
    
    /**
     * Get all messages a user is tagged in that he/she has not been notified of yet.
     * @return 
     */
    public List<Message> getMessagesForMentionEmail(String uid){   
        List<Message> result = new Vector<Message>();
        List<MessageToUser> messageToUsers = super.findAll();
        for(MessageToUser tuple : messageToUsers){
            if(tuple.getUserUid().equals(uid) && tuple.getIsTagged() && !tuple.getHasBeenNotified()){
                result.add(tuple.getMessageId());
                tuple.setHasBeenNotified(true); //NEED TO PERSIST THIS
                super.edit(tuple);
            }
        }
        return result;
    }
    
    /**
     * Gets an specific MessageToUser entity given a user id and message.
     * Null if no association exists.
     * @param uid
     * @param message
     * @return 
     */
    public MessageToUser getMessageAssociation(String uid, Message message){
        List<MessageToUser> messageToUsers = super.findAll();
        for(MessageToUser tuple : messageToUsers){
            boolean isUser = tuple.getUserUid().equals(uid);
            boolean isMessage = tuple.getMessageId().equals(message);
            if(isUser && isMessage){
                return tuple;
            }
        }
        return null;
    }
    
    /**
     * Extract the user IDs of the tagged individuals in a message.
     * @param message
     * @return List of User ids
     */
    public ArrayList<String> parseMessageTags(Message message){
        ArrayList<String> tags = new ArrayList<String>();
        String body = message.getBody();
        
        String[] words = body.split(" ");
        for ( String word : words) {
            if(word.charAt(0)=='@'){
                String uid = word.substring(1);
                tags.add(uid);
            }
        }
        
        return tags;
    }
    
    /**
     * Create an association for each provided user to the provided message setting isTagged to true.
     * If the user is already associated with the message then update entity instead of create.
     * @param uids 
     * @param message
     */
    public void createAssociationsForTaggedUsers(ArrayList<String> uids, Message message){
        for(String uid : uids){
            MessageToUser association = getMessageAssociation(uid, message);
            if(association == null){
                MessageToUser newAssociation = new MessageToUser(null, uid, false, true, false, false, message);
                super.create(newAssociation);
            }
            else{
                association.setIsTagged(true);
                super.edit(association);
            }
        }
    }
}
