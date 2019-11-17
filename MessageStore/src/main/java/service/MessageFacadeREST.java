/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Message;
import entities.SystemUser;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
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
public class MessageFacadeREST extends AbstractFacade<Message> {
    
    /**
     * Entity Manager to interact with persistence context (PostgreSQL Database).
     */
    @PersistenceContext(unitName = "com.mycompany_RestJpa_war_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    /**
     * Use uriInfo to get current context path and to build HATEOAS links.
    * */
    @Context
    UriInfo uriInfo;
    
    /**
     * Access through facade to CRUD operations on User entity.
     */
    @EJB
    private SystemUserFacade userFacade;
    
    /**
     * Logger.
     */
    private final static Logger LOG = Logger.getLogger(MessageFacadeREST.class.getName());

    public MessageFacadeREST() {
        super(Message.class);
        LOG.setLevel(Level.ALL);
    }

    /**
     * Creates a new message.
     * @param entity - message to be created.
     * @return 201 Created HTTP response upon successful creation.
     */
    @POST
    @Consumes({"application/xml", "application/json"})
    public Response createMessage(Message entity) {
        LOG.log(Level.INFO, "ENTRY to createMessage() action. Reponding to POST: {0}", entity);
        
        /* If owning user entity already exists then manually
        ensure asscoaition becasue CascadeType.MEGRE is broken */
        if(entity.getUserUid() != null){ //if user provided
            SystemUser user = userFacade.find(entity.getUserUid().getUid());
            if(user != null){ //if user already in database
                //manually asscoiate new message to user
                entity.setUserUid(user);
            }
        }
        
        super.create(entity);
        LOG.info("Message successfully created.");
        
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
    @Consumes({"application/xml", "application/json"})
    public Response update(@PathParam("id") Integer id, Message entity) {
        Object[] params = { id, entity }; 
        LOG.log(Level.INFO, "ENTRY to update() action.  URL Path parameter: {0}. Reponding to PUT: {1}.", params);
        
        Message message = super.find(id);
        if(message == null){
            LOG.log(Level.WARNING, "Message with id {0} does not exist.", id);
            return Response.status(404).build();
        }
        
        /* If owning user entity already exists then manually
        ensure asscoaition becasue CascadeType.MEGRE is broken */
        if(entity.getUserUid() != null){ //if user provided
            SystemUser user = userFacade.find(entity.getUserUid().getUid());
            if(user != null){ //if user already in database
                //manually asscoiate new message to user
                entity.setUserUid(user);
            }
        }
        
        /* Ensure the id is set on the entity so merge() will update it */
        entity.setId(id);
        super.edit(entity);
        LOG.info("Message successfully updated.");
        
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
        
        Message message = super.find(id);
        if(message == null){
            LOG.log(Level.WARNING, "Message with id {0} does not exist.", id);
            return Response.status(404).build();
        }
        
        super.remove(message);
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
    @Produces({"application/xml", "application/json"})
    public Response retrieveById(@PathParam("id") Integer id) {
        LOG.log(Level.INFO, "ENTRY to retrieveById() action. URL Path Parameter: {0}. Reponding to GET.", id);
        
        Message message = super.find(id);
        if(message == null){
            LOG.log(Level.WARNING, "Message with id {0} does not exist.", id);
            return Response.status(404).build();
        }
        
        return Response.status(Status.OK).build();
    }

    /**
     * Get all messages.
     * @return A list of all messages in XML or JSON format.
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public Response retrieveAll() {
        LOG.log(Level.INFO, "ENTRY to retrieveAll() action. Reponding to GET.");
        
        List<Message> messages = super.findAll();
        /* Need to wrap collection in GenericType to return it in Response*/
        GenericEntity<List<Message>> wrappedMessages = new GenericEntity<List<Message>>(messages) {};
        return Response
            .status(Response.Status.OK)
            .entity(wrappedMessages)
            .build();
    }
    
    /**
     * Gets a list of all messages from a specific group.
     * @param groupId
     * @return All messages belonging to a specified group (as JSON or XMl)
     */
    @GET
    @Path("group/{group_id}")
    @Produces({"application/xml", "application/json"})
    public Response retreiveAllByGroup(@PathParam("group_id") Integer groupId) {
        LOG.log(Level.INFO, "ENTRY to retreiveAllByGroup() action. URL Path Parameter: {0}. Reponding to GET.", groupId);
        
        List<Message> messages = super.findAll();
        List<Message> messagesInGroup = new ArrayList<>();
        
        /* Filter list to match group id */
        for(Message m : messages){
            if(m.getGroupId()==groupId){
                messagesInGroup.add(m);
            }
        }
        
        /* Need to wrap collection in GenericType to return it in Response */
        GenericEntity<List<Message>> wrappedMessages = new GenericEntity<List<Message>>(messagesInGroup) {};
        return Response
            .status(Response.Status.OK)
            .entity(wrappedMessages)
            .build();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
