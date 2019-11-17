/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entities.SystemUser;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Morgan
 */
@Stateless
public class SystemUserFacade extends AbstractFacade<SystemUser> {
    
    /**
     * Entity Manager to interact with persistence context (PostgreSQL Database).
     */
    @PersistenceContext(unitName = "com.mycompany_RestJpa_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    public SystemUserFacade() {
        super(SystemUser.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
