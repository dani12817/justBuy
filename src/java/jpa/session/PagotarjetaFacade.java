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
import jpa.entity.Pagotarjeta;
import jpa.entity.Usuario;

/**
 *
 * @author Dani
 */
@Stateless
public class PagotarjetaFacade extends AbstractFacade<Pagotarjeta> {

    @PersistenceContext(unitName = "justBuy-danielLealPerezPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PagotarjetaFacade() {
        super(Pagotarjeta.class);
    }
    
    public List<Pagotarjeta> buscarPorUsuario(Usuario usuario){
        return em.createQuery("SELECT p FROM Pagotarjeta p WHERE p.usuario = :usuario")
                .setParameter("usuario", usuario)
                .getResultList();
    }
    
    public Pagotarjeta getNuevaTarjetaPedido(Pagotarjeta tarjeta){
        return (Pagotarjeta) em.createQuery("SELECT p FROM Pagotarjeta p WHERE p.usuario = :usuario AND p.titular = :titular AND  p.numeroTarjeta = :numeroTarjeta")
                .setParameter("usuario", tarjeta.getUsuario()).setParameter("titular", tarjeta.getTitular()).setParameter("numeroTarjeta", tarjeta.getNumeroTarjeta())
                .getResultList().get(0);
    }
    
}
