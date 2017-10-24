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
import jpa.entity.Mensaje;
import jpa.entity.Usuario;

/**
 *
 * @author dleal
 */
@Stateless
public class MensajeFacade extends AbstractFacade<Mensaje> {

    @PersistenceContext(unitName = "justBuy-danielLealPerezPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MensajeFacade() {
        super(Mensaje.class);
    }
    
    public List<Mensaje> encontrarMensajesRecibidos(Usuario usuario){
        return em.createQuery("SELECT m FROM Mensaje m WHERE m.destinatario = :usuario")
                .setParameter("usuario", usuario)
                .getResultList();
    }
    
    public List<Mensaje> encontrarMensajesEnviados(Usuario usuario){
        return em.createQuery("SELECT m FROM Mensaje m WHERE m.remitente = :usuario")
                .setParameter("usuario", usuario)
                .getResultList();
    }
    
}
