/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Message;
import entities.MessageToUser;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Morgan
 */
@Stateless
public class MessageFacade extends AbstractFacade<Message>{
    
    /**
     * Entity Manager to interact with persistence context (PostgreSQL Database).
     */
    @PersistenceContext(unitName = "com.mycompany_RestJpa_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public MessageFacade() {
        super(Message.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
