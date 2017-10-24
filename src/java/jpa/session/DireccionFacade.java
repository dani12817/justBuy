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
import jpa.entity.Direccion;
import jpa.entity.Usuario;

/**
 *
 * @author Dani
 */
@Stateless
public class DireccionFacade extends AbstractFacade<Direccion> {

    @PersistenceContext(unitName = "justBuy-danielLealPerezPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DireccionFacade() {
        super(Direccion.class);
    }
    
    public List<Direccion> getDireccionesPorUsuario(Usuario usuario){
        return em.createQuery("SELECT d FROM Direccion d WHERE d.usuario = :usuario")
                .setParameter("usuario", usuario)
                .getResultList();
    }
    
    public Direccion getnuevaDireccionPedido(Direccion d){
        return (Direccion) em.createQuery("SELECT d FROM Direccion d WHERE d.usuario = :usuario AND d.nombre = :nombre AND d.direccionLinea1 = :direccionLinea1 "
                + "AND d.direccionLinea2 = :direccionLinea2 AND d.codPostal = :codPostal")
                .setParameter("usuario", d.getUsuario()).setParameter("direccionLinea1", d.getDireccionLinea1())
                .setParameter("direccionLinea2", d.getDireccionLinea2()).setParameter("codPostal", d.getCodPostal())
                .setParameter("nombre", d.getNombre())
                .getResultList().get(0);
    }
    
}
