/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Message;
import entities.MessageToUser;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.embeddable.EJBContainer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Morgan
 */
public class MessageToUserFacadeTest {
    
    public MessageToUserFacadeTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }


    /**
     * Test of find method, of class MessageToUserFacade.
     */
//    @Test
//    public void testGetMessageAssociationWhenThereIsOne() throws Exception {
//
//        Map<String, Object> properties = new HashMap<String, Object>();
//        properties.put(
//            "org.glassfish.ejb.embedded.glassfish.configuration.file",
//            "C:/glassfish5/glassfish/domains/domain1/config/domain.xml"
//        );
//        
//        properties.put(EJBContainer.MODULES, new File("target/classes"));
//        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer(properties);
//        MessageToUserFacade instance = (MessageToUserFacade)container.getContext().lookup("java:global/classes/MessageToUserFacade");
//        
//        String uid = "mwj7";
//        Date now = new Date();
//        Message message  = new Message(56, "test message body", "mwj7", false, false, now, now, 1);
//        MessageToUser expectedAssoc = new MessageToUser(24, "mwj7", true, false, true, false);
//       
//        MessageToUser assoc = null;
//        try{
//            assoc = instance.getMessageAssociation(uid, message);
//        }catch(NullPointerException e){
//            fail("There is a association but it was not returned.");
//        }
//        
//        assertEquals(expectedAssoc, assoc);
//        
//        container.close();
//    }


}
