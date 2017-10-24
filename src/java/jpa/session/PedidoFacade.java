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
 * @author dleal
 */
@Stateless
public class PedidoFacade extends AbstractFacade<Pedido> {

    @PersistenceContext(unitName = "justBuy-danielLealPerezPU")
    private EntityManager em;
    
    private List<Pedido> listaPedidos;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PedidoFacade() {
        super(Pedido.class);
    }
    
    public int getSiguienteId(){        
        return findAll().size()+1;
    }
    
    public List<Pedido> pedidosPorUsuario(Usuario usuario){
        return em.createQuery("SELECT p FROM Pedido p WHERE p.usuario = :usuario")
                .setParameter("usuario", usuario)
                .getResultList();
    }
    
    public List<Pedido> pedidosPorTienda(Tienda tienda){
        return em.createQuery("SELECT p FROM Pedido p WHERE p.tienda = :tienda")
                .setParameter("tienda", tienda)
                .getResultList();
    }
    
}
