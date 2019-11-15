/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Message;
import java.util.List;
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
import javax.ws.rs.core.UriInfo;

/**
 * 
 * @author nwh
 */
@Stateless
@Path("messages")
public class MessageFacadeREST extends AbstractFacade<Message> {
    @PersistenceContext(unitName = "com.mycompany_RestJpa_war_1.0-SNAPSHOTPU")
    private EntityManager em;
    
    /**
     * Use uriInfo to get current context path and to build HATEOAS links 
    * */
    @Context
    UriInfo uriInfo;

    public MessageFacadeREST() {
        super(Message.class);
    }

    /**
     *
     * @param entity
     * @return
     */
    @POST
    @Consumes({"application/xml", "application/json"})
    public Response createMessage(Message entity) {
//        if(entity == null)  {
//            return Response.status(Response.Status.BAD_REQUEST)
//                .entity("Config content not found")
//                .build();
//        }
 
        super.create(entity);
        Link lnk = Link.fromUri(uriInfo.getPath() + "/" + entity.getId()).rel("self").build();
        return Response.status(javax.ws.rs.core.Response.Status.CREATED).location(lnk.getUri()).build();
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void update(@PathParam("id") Integer id, Message entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Integer id) {
        Response response;
        Message message = super.find(id);
        
        if(message != null){
            super.remove(message);
            response = Response
            .status(Response.Status.OK)
            .build();
        }
        else{
            response = Response
            .status(Response.Status.NOT_FOUND)
            .build(); 
        }
        
        return response;
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Response retrieve(@PathParam("id") Integer id) {
        
        Response response;
        Message message = super.find(id);
        
        if(message != null){
            response = Response
            .status(Response.Status.OK)
            .entity(message)
            .build();
        }
        else{
            response = Response
            .status(Response.Status.NOT_FOUND)
            .build(); 
        }
        
        return response;
    }

    @GET
    @Produces({"application/xml", "application/json"})
    public Response retrieveAll() {
        List<Message> messages = super.findAll();
        /* Need to wrap collection in GenericType to return it in Response*/
        GenericEntity<List<Message>> wrappedMessages = new GenericEntity<List<Message>>(messages) {};
        return Response
            .status(Response.Status.OK)
            .entity(wrappedMessages)
            .build();
    }

   // UNEEDED METHODS BELOW REMOVE ???
//    @GET 
//    @Path("{from}/{to}")
//    @Produces({"application/xml", "application/json"})
//    public List<Message> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
//        return super.findRange(new int[]{from, to});
//    }
//
//    @GET
//    @Path("count")
//    @Produces("text/plain")
//    public String countREST() {
//        return String.valueOf(super.count());
//    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
