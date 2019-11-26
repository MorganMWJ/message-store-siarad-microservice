/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Message;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import org.eclipse.persistence.jaxb.MarshallerProperties;

/**
 *
 * @author Morgan
 */
@Provider
@Produces("application/json")
public class MessageListMessageBodyWriter implements MessageBodyWriter<List<Message>>{

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return Vector.class==type;
    }

    @Override
    public long getSize(List<Message> t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(List<Message> t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
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
            jaxbMarshaller.marshal(t, new PrintWriter(System.out));            
            jaxbMarshaller.marshal(t, entityStream);    
        } catch (JAXBException ex) {
            Logger.getLogger(MessageStoreREST.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}
