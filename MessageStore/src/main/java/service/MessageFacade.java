/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.Message;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Morgan
 */
public class MessageFacade extends AbstractFacade<Message>{

    public MessageFacade(Class<Message> entityClass) {
        super(entityClass);
    }

    @Override
    protected EntityManager getEntityManager() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_MessageStore_war_1.0-SNAPSHOTPU");
        EntityManager em =  emf.createEntityManager();
        return em;
    }
    
}
