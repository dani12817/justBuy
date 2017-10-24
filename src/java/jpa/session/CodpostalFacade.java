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
public class CodpostalFacade extends AbstractFacade<Codpostal> {

    @PersistenceContext(unitName = "justBuy-danielLealPerezPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CodpostalFacade() {
        super(Codpostal.class);
    }
    
    public Codpostal getByNumeroPostal(String numero){
        
        List<Codpostal> listaPostal = em.createQuery("SELECT c FROM Codpostal c WHERE c.numero = :numero").setParameter("numero", numero).getResultList();
        if(!listaPostal.isEmpty()){
            return listaPostal.get(0);
        }else{
            return null;
        }
    }
    
    public List<Codpostal> getByCiudad(Ciudad ciudad){
        
        return em.createQuery("SELECT c FROM Codpostal c WHERE c.ciudad = :ciudad").setParameter("ciudad", ciudad).getResultList();        
        
    }
    
}
