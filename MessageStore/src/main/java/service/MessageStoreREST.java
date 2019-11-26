/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;


import entities.Message;
import entities.MessageToUser;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
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
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.eclipse.persistence.jaxb.MarshallerProperties;

/**
 * REST service/API provided for Message Store Resource.
 * @author Morgan Jones
 */
@Stateless
@Path("messages")
public class MessageStoreREST {
    
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
     * Access through facade to CRUD operations on MessageToUser entity.
     */
    @EJB
    private MessageToUserFacade messageToUserFacade;
    
    /**
     * Access through facade to CRUD operations on MessageToUser entity.
     */
    @EJB
    private MessageFacade messageFacade;
    
//    private Gson gson;
    
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
        
        /* Ensure the id is set on the entity so merge() will update it */
        entity.setId(id);
        messageFacade.edit(entity);
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
     * @return Response containing the messages
     */
    @GET
    @Path("user/{uid}")
    @Produces("application/json")
    public Response retreiveAllByUser(@PathParam("uid") String uid){
        LOG.log(Level.INFO, "ENTRY to retreiveAllByUser() action. URL Path Parameter: {0}. Reponding to GET.", uid);       
        List<Message> messages = messageFacade.getMessagesById(uid);     
    
        /* Need to wrap collection in GenericType to return it in Response*/
        GenericEntity<List<Message>> wrappedMessages = new GenericEntity<List<Message>>(messages) {};
        return Response
            .status(Response.Status.OK)
            .entity(wrappedMessages)
            .build();
    }
    
    /**
     * Get all messages.
     * @return A list of all messages in JSON format.
     */
    @GET
    @Produces("application/json")
    public List<Message> retrieveAll() {
        LOG.log(Level.INFO, "ENTRY to retrieveAll() action. Reponding to GET.");        
        List<Message> messages = messageFacade.findAll();   
        
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(Message.class);
            Marshaller jaxbMarshaller   = jaxbContext.createMarshaller();
            // To format JSON
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);              
            //Set JSON type
            jaxbMarshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
            jaxbMarshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, true);
            //Overloaded methods to marshal to different outputs            
            jaxbMarshaller.marshal( messages.get(0), new PrintWriter( System.out ) );
        } catch (JAXBException ex) {
            Logger.getLogger(MessageStoreREST.class.getName()).log(Level.SEVERE, null, ex);
        }


//        /* Need to wrap collection in GenericType to return it in Response*/
//        GenericEntity<List<Message>> wrappedMessages = new GenericEntity<List<Message>>(messages) {};
//        return Response
//            .status(Response.Status.OK)
//            .entity(wrappedMessages)
//            .build();
        return messages;
    }
    
    @GET
    @Path("replies/{uid}")
    @Produces("application/json")
    public Response getReplySummary(@PathParam("uid") String uid){        
        List<Message> messages = messageToUserFacade.getMessagesForReplyEmail(uid);     
        
        /* Need to wrap collection in GenericType to return it in Response*/
        GenericEntity<List<Message>> wrappedMessages = new GenericEntity<List<Message>>(messages) {};
        
        return Response
            .status(Response.Status.OK)
            .entity(wrappedMessages)
            .build();
    }
    
    @GET
    @Path("mentions/{uid}")
    @Produces("application/json")
    public Response getMentionSummary(@PathParam("uid") String uid){        
        return null;
    }
    
    @GET
    @Path("daily/{uid}")
    @Produces("application/json")
    public Response getDailySummary(@PathParam("uid") String uid){        
        return null;
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
}
