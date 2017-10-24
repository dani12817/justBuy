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
import jpa.entity.Lineapedido;
import jpa.entity.Pedido;

/**
 *
 * @author dleal
 */
@Stateless
public class LineapedidoFacade extends AbstractFacade<Lineapedido> {

    @PersistenceContext(unitName = "justBuy-danielLealPerezPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LineapedidoFacade() {
        super(Lineapedido.class);
    }
    
    public List<Lineapedido> productosDeUnPedido(Pedido pedido){
        return em.createQuery("SELECT l FROM Lineapedido l WHERE l.pedido = :pedido")
                .setParameter("pedido", pedido)
                .getResultList();
    }
}
