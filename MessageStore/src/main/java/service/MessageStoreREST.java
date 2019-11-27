/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;


import entities.Message;
import entities.MessageToUser;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

/**
 * REST service/API provided for Message Store Resource.
 * @author Morgan Jones
 */
@Stateless
@Path("messages")
public class MessageStoreREST {
        
    /**
     * Use uriInfo to get current context path and to build HATEOAS links.
    * */
    @Context
    UriInfo uriInfo;
    
    /**
     * Access through facade to CRUD operations on MessageToUser entity.
     */
    @EJB
    private MessageToUserFacade messageToUserFacade;
    
    /**
     * Access through facade to CRUD operations on MessageToUser entity.
     */
    @EJB
    private MessageFacade messageFacade;
    
    /**
     * Logger.
     */
    private final static Logger LOG = Logger.getLogger(MessageStoreREST.class.getName());

    public MessageStoreREST() {
        LOG.setLevel(Level.ALL);
    }

    /**
     * Creates a new message.
     * @param entity - message to be created.
     * @return 201 Created HTTP response upon successful creation.
     */
    @POST
    @Consumes("application/json")
    public Response createMessage(Message entity) {
        LOG.log(Level.INFO, "ENTRY to createMessage() action. Reponding to POST: {0}", entity);
        
        messageFacade.create(entity);
        LOG.info("Message successfully created.");
        
        if(entity.getOwnerUid()!=null){
            /* Need to now create a default association for the owning user of that message */
            MessageToUser association = new MessageToUser(null, entity.getOwnerUid(), true, false, true, false, entity);
            messageToUserFacade.create(association);
        }
        
        /* Create associations for those users tagged in the new message */
        ArrayList<String> tags = messageToUserFacade.parseMessageTags(entity);
        messageToUserFacade.createAssociationsForTaggedUsers(tags, entity);
        
        Link lnk = Link.fromUri(uriInfo.getPath() + "/" + entity.getId()).rel("self").build();
        return Response.status(Response.Status.CREATED).location(lnk.getUri()).build();
    }

    /**
     * Updates a single message.
     * @param id - ID of the message to update.
     * @param entity - new message content to update old one with .
     * @return 404 if message doesn't exists or 200 OK if updated successfully.
     */
    @PUT
    @Path("{id}")
    @Consumes("application/json")
    public Response update(@PathParam("id") Integer id, Message entity) {
        Object[] params = { id, entity }; 
        LOG.log(Level.INFO, "ENTRY to update() action.  URL Path parameter: {0}. Reponding to PUT: {1}.", params);
        
        Message message = messageFacade.find(id);
        if(message == null){
            LOG.log(Level.WARNING, "Message with id {0} does not exist.", id);
            return Response.status(404).build();
        }
        
        /* Not allowed to update parent message ID */
        if(entity.getParentMessageId() != message.getParentMessageId()){
            LOG.log(Level.SEVERE, "Not allowed to update parent message ID.");
        }
        
        /* In case id=null ensure the id is set on the entity so merge() will update it */
        entity.setId(id);
        
        /* Keep the messages replies so an update will not remove them all as children */
        entity.setMessageCollection(message.getMessageCollection());
        
        messageFacade.edit(entity);        
        LOG.info("Message successfully updated.");
        
        /* Create associations for those users tagged in the new message */
        ArrayList<String> tags = messageToUserFacade.parseMessageTags(entity);
        messageToUserFacade.createAssociationsForTaggedUsers(tags, entity);
        
        return Response.status(Status.OK).build();
    }

    /**
     * Delete a specific message.
     * @param id - ID of the message to delete.
     * @return 404 if message doesn't exist or 200 OK if message deleted successfully.
     */
    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Integer id) {
        LOG.log(Level.INFO, "ENTRY to delete() action. URL Path Parameter: {0}. Reponding to DELETE.", id);
        
        Message message = messageFacade.find(id);
        if(message == null){
            LOG.log(Level.WARNING, "Message with id {0} does not exist.", id);
            return Response.status(404).build();
        }
        
        messageFacade.remove(message);
        LOG.info("Message successfully deleted.");
        
        return Response.status(Status.OK).build();
    }

    /**
     * Get a message by ID.
     * @param id - ID of the message to return.
     * @return Response containing the message in JSON or XML format,
     * or 404 if message doesn't exist.
     */
    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response retrieveById(@PathParam("id") Integer id) {
        LOG.log(Level.INFO, "ENTRY to retrieveById() action. URL Path Parameter: {0}. Reponding to GET.", id);
        
        Message message = messageFacade.find(id);
        if(message == null){
            LOG.log(Level.WARNING, "Message with id {0} does not exist.", id);
            return Response.status(404).build();
        }
        
        return Response.status(Status.OK).entity(message).build();
    }
    
    /**
     * Get all a user's messages.
     * @param uid
     * @return Response containing the messages
     */
    @GET
    @Path("user/{uid}")
    @Produces("application/json")
    public Response retreiveAllByUser(@PathParam("uid") String uid){
        LOG.log(Level.INFO, "ENTRY to retreiveAllByUser() action. URL Path Parameter: {0}. Reponding to GET.", uid);       
        List<Message> messages = messageFacade.getOwnedMessages(uid);     
    
        return Response
            .status(Response.Status.OK)
            .entity(messages)
            .build();
    }
    
    /**
     * Get all messages.
     * @return A list of all messages in JSON format.
     */
    @GET
    @Produces("application/json")
    public Response retrieveAll() {
        LOG.log(Level.INFO, "ENTRY to retrieveAll() action. Reponding to GET.");        
        List<Message> messages = messageFacade.findAll();
        
        return Response
            .status(Response.Status.OK)
            .entity(messages)
            .build();
    }
    
    /**
     * Get the messages required for a given user's reply summary.
     * @param uid
     * @return
     */
    @GET
    @Path("replies/{uid}")
    @Produces("application/json")
    public Response getReplySummary(@PathParam("uid") String uid){        
        List<Message> messages = messageToUserFacade.getMessagesForReplyEmail(uid);     
                
        return Response
            .status(Response.Status.OK)
            .entity(messages)
            .build();
    }
    
    /**
     * Get the messages required for a given user's mentions summary.
     * @param uid
     * @return
     */
    @GET
    @Path("mentions/{uid}")
    @Produces("application/json")
    public Response getMentionSummary(@PathParam("uid") String uid){        
        List<Message> messages = messageToUserFacade.getMessagesForMentionEmail(uid);     
                
        return Response
            .status(Response.Status.OK)
            .entity(messages)
            .build();
    }
    
    /**
     * Get the messages required for a given user's daily summary.
     * @param uid
     * @return
     */
    @GET
    @Path("daily/{uid}")
    @Produces("application/json")
    public Response getDailySummary(@PathParam("uid") String uid){        
        List<Message> messages = messageToUserFacade.getMessagesForDailyEmail(uid);     
                
        return Response
            .status(Response.Status.OK)
            .entity(messages)
            .build();
    }
    
    /**
     * Gets a list of all messages from a specific group.
     * @param groupId
     * @return All messages belonging to a specified group (as JSON or XMl)
     */
    @GET
    @Path("group/{group_id}")
    @Produces("application/json")
    public Response retreiveAllByGroup(@PathParam("group_id") Integer groupId) {
        LOG.log(Level.INFO, "ENTRY to retreiveAllByGroup() action. URL Path Parameter: {0}. Reponding to GET.", groupId);
        
        List<Message> messages = messageFacade.findAll();
        List<Message> messagesInGroup = new Vector<>();
        
        /* Filter list to match group id */
        for(Message m : messages){
            if(m.getGroupId()==groupId){
                messagesInGroup.add(m);
            }
        }
        
        return Response
            .status(Response.Status.OK)
            .entity(messagesInGroup)
            .build();
    }
}
