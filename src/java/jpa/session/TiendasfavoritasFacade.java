/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpa.entity.Tiendasfavoritas;

/**
 *
 * @author dleal
 */
@Stateless
public class TiendasfavoritasFacade extends AbstractFacade<Tiendasfavoritas> {

    @PersistenceContext(unitName = "justBuy-danielLealPerezPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TiendasfavoritasFacade() {
        super(Tiendasfavoritas.class);
    }
    
}
