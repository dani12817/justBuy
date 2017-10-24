/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpa.entity.Pagopaypal;
import jpa.entity.Usuario;
import java.util.List;

/**
 *
 * @author Dani
 */
@Stateless
public class PagopaypalFacade extends AbstractFacade<Pagopaypal> {

    @PersistenceContext(unitName = "justBuy-danielLealPerezPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PagopaypalFacade() {
        super(Pagopaypal.class);
    }
    
    public List<Pagopaypal> buscarPorUsuario(Usuario usuario){
        return em.createQuery("SELECT p FROM Pagopaypal p WHERE p.usuario = :usuario")
                .setParameter("usuario", usuario)
                .getResultList();
    }
    
    public Pagopaypal getNuevaPaypalPedido(Pagopaypal paypal){
        return (Pagopaypal) em.createQuery("SELECT p FROM Pagopaypal p WHERE p.usuario = :usuario AND p.titular = :titular AND  p.mail = :mail")
                .setParameter("usuario", paypal.getUsuario()).setParameter("titular", paypal.getTitular()).setParameter("mail", paypal.getMail())
                .getResultList().get(0);
    }
    
}
