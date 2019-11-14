/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Message;
import entities.SystemUser;
import java.util.Date;
import java.util.List;
import javax.ejb.Singleton;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Morgan
 */
@Path("messages")
//@Singleton - this causes error: java.lang.IllegalStateException: Error when configuring to use the EJB interceptor binding API. JAX-RS EJB integration can not be supported.
public class MessageStoreResource {

    @Context
    private UriInfo context;
    private MessageFacade messageFacade;


    /**
     * Creates a new instance of GenericResource
     */
    public MessageStoreResource() {
        this.messageFacade = new MessageFacade(Message.class);
    }
    
    
    /**
     *  Returns a list of messages as a JSON in the HTTP response body
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Response RetreiveAll(){
        List<Message> allMessages = messageFacade.findAll();
        System.out.println(allMessages.size());
        System.out.println(allMessages.get(0));
        return Response
            .status(Response.Status.OK)
            .entity(allMessages.get(0))
            .build();
    }
   
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(String payload){
        
        SystemUser user = new SystemUser("mwj7");
        
        Date now = new Date();
        Message message = new Message(); 
        message.setBody("Hi there everyone this is my message.");
        message.setIsDeleted(false);
        message.setHasReplies(false);
        message.setTimeCreated(now);
        message.setTimeEdited(now);
        message.setGroupId(1);
        message.setUserUid(user);
        messageFacade.create(message);

        return Response
            .status(Response.Status.CREATED)
            .entity("Message created successfully")
            .build();
    }
    
    @GET
    @Path("hello")
    public Response hello(){
        return Response
         .status(Response.Status.OK)
         .entity("it works!")
         .build();
    }
    
    @POST @Path("testpost")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response postHtml(String message) {
       return Response
            .status(Response.Status.CREATED)
            .entity("Recieved message: " + message)
            .build();
    }

//    /**
//     * Retrieves representation of an instance of service.GenericResource
//     * @return an instance of java.lang.String
//     */
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public String getJson() {
//        //TODO return proper representation object
//        throw new UnsupportedOperationException();
//    }
//
//    /**
//     * PUT method for updating or creating an instance of GenericResource
//     * @param content representation for the resource
//     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response putJson(String content) {
        return Response
         .status(Response.Status.OK)
         .entity(content)
         .build();
    }
}
