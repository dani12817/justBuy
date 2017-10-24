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
import jpa.entity.Envio;
import jpa.entity.Tienda;

/**
 *
 * @author dleal
 */
@Stateless
public class EnvioFacade extends AbstractFacade<Envio> {

    @PersistenceContext(unitName = "justBuy-danielLealPerezPU")
    private EntityManager em;
    private List<Envio> enviosTienda;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<Envio> findPorTienda(Tienda miTienda){
        return em.createQuery("SELECT e FROM Envio e WHERE e.tienda = :tienda")
                .setParameter("tienda", miTienda)
                .getResultList();
    }
    
    public List<Envio> findPorTiendaTramitar(Tienda miTienda){
        return em.createQuery("SELECT e FROM Envio e WHERE e.tienda = :tienda OR e.idenvio = 0")
                .setParameter("tienda", miTienda)
                .getResultList();
    }
    
    public Envio findUnEnvioPorTienda(Tienda miTienda){
        enviosTienda = em.createQuery("SELECT e FROM Envio e WHERE e.tienda = :tienda OR e.idenvio = 0")
                .setParameter("tienda", miTienda)
                .getResultList();
        
        if(enviosTienda.size() > 1){
            return enviosTienda.get(1);
        }
        
        return enviosTienda.get(0);
    }

    public EnvioFacade() {
        super(Envio.class);
    }
    
}
