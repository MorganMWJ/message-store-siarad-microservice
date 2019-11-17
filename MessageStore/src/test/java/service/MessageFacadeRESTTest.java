/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Message;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.ws.rs.core.Response;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

/**
 *
 * @author Morgan Jones
 */
public class MessageFacadeRESTTest{    
    
    private static Context  ctx;
    private static EJBContainer ejbContainer;
    
    private static Connection CONN;
    private final static String URL = "jdbc:postgresql://db.dcs.aber.ac.uk/sem5640_19_20_mwj7";
    private final static String USER = "mwj7";
    private final static String PASSWORD = "admin";
    
    private final static String SET_UP_SQL_DIRECTORY = "../../../main/resources/META-INF/sql/testSetUp.sql";
    private final static String TEAR_DOWN_SQL_DIRECTORY = "../../../main/resources/META-INF/sql/testTearDown.sql";

    @BeforeClass
    public static void setUpClass() throws IOException {
        ejbContainer = EJBContainer.createEJBContainer();
        System.out.println("Container Opening" );
        ctx = ejbContainer.getContext();
//        try {
//            //Class.forName("org.postgresql.Driver");
//            CONN = DriverManager.getConnection(URL, USER, PASSWORD);
//            System.out.println("Connected to the PostgreSQL server successfully.");
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        } 
//        
//        List<String> sequelCode = Files.readAllLines(new File(SET_UP_SQL_DIRECTORY).toPath(), Charset.defaultCharset());
//        for(String line : sequelCode){
//            System.out.println(line);
//        }
    }
    
    
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
        
    }
    
    @After
    public void tearDown() throws Exception {
        
    }
    
    @Test
    public void testRetrieveAllMessages() throws NamingException {
        MessageFacadeREST instance = (MessageFacadeREST) ctx.lookup("service.MessageFacadeREST");
        
        Response resultResponse = instance.retrieveAll();
        
        System.out.println(resultResponse.getEntity());
    }
    
}
