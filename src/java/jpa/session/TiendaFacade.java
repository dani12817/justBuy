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
public class TiendaFacade extends AbstractFacade<Tienda> {

    @PersistenceContext(unitName = "justBuy-danielLealPerezPU")
    private EntityManager em;
    private List<Tienda> tiendas;
    private Tienda tiendaUsuario;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TiendaFacade() {
        super(Tienda.class);
    }
    
    public List<Tienda> findByNickName(Codpostal codpostal){
        return em.createQuery("SELECT t "
                + "FROM Tienda t "
                + "WHERE t.codPostal = :codpostal AND t.tiendaActiva = 1")
                .setParameter("codpostal", codpostal)
                .getResultList();
    } 
    
    public Tienda findPorUsuario(Usuario usuario){
        tiendas = em.createQuery("SELECT t FROM Tienda t WHERE t.usuario = :usuario")
                .setParameter("usuario", usuario)
                .getResultList();
        
        if(tiendas.size() > 0){
            return tiendas.get(0);
        }
        
        return null;
    }
    
    public boolean tiendaPendiente(Usuario usuario){
        tiendas = em.createQuery("SELECT t FROM Tienda t WHERE t.usuario = :usuario")
                .setParameter("usuario", usuario)
                .getResultList();
        
        System.out.println("Tienda -> "+tiendas.size());
        
        if(tiendas.size() > 0){
            tiendaUsuario = tiendas.get(0);
            return (tiendaUsuario.getTiendaActiva() == 0); 
        }else{
            return true;
        }        
               
    } 
    
    public boolean hayTienda(Usuario usuario){
        tiendas = em.createQuery("SELECT t FROM Tienda t WHERE t.usuario = :usuario")
                .setParameter("usuario", usuario)
                .getResultList();
        
        if(tiendas.size() > 0){
            return true;
        }else{
            return false;
        }        
               
    } 
    
    public void createTienda(Tienda tienda){
        create(tienda);
    } 
    
}
