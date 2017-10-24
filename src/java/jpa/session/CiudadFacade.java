/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpa.entity.*;

/**
 *
 * @author Dani
 */
@Stateless
public class CiudadFacade extends AbstractFacade<Ciudad> {

    @PersistenceContext(unitName = "justBuy-danielLealPerezPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CiudadFacade() {
        super(Ciudad.class);
    }
    
    public List<Ciudad> getByProvincia(Provincia provincia){
        
        return em.createQuery("SELECT c FROM Ciudad c WHERE c.provincia = :provincia").setParameter("provincia", provincia).getResultList();        
        
    }
    
}
