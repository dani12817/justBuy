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
import jpa.entity.Tienda;
import jpa.entity.Valoracion;

/**
 *
 * @author dleal
 */
@Stateless
public class ValoracionFacade extends AbstractFacade<Valoracion> {

    @PersistenceContext(unitName = "justBuy-danielLealPerezPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<Valoracion> getValoracionesTienda(Tienda miTienda) {
        return em.createQuery("SELECT v FROM Valoracion v WHERE v.tienda = :tienda")
                .setParameter("tienda", miTienda)
                .getResultList();
    }
    
    public int getValoraciones6(Tienda miTienda) {
        return em.createQuery("SELECT v FROM Valoracion v WHERE v.tienda = :tienda AND v.puntuacion = 6")
                .setParameter("tienda", miTienda)
                .getResultList().size();
    }
    
    public int getValoraciones5(Tienda miTienda) {
        return em.createQuery("SELECT v FROM Valoracion v WHERE v.tienda = :tienda AND v.puntuacion = 5")
                .setParameter("tienda", miTienda)
                .getResultList().size();
    }
    
    public int getValoraciones4(Tienda miTienda) {
        return em.createQuery("SELECT v FROM Valoracion v WHERE v.tienda = :tienda AND v.puntuacion = 4")
                .setParameter("tienda", miTienda)
                .getResultList().size();
    }
    
    public int getValoraciones3(Tienda miTienda) {
        return em.createQuery("SELECT v FROM Valoracion v WHERE v.tienda = :tienda AND v.puntuacion = 3")
                .setParameter("tienda", miTienda)
                .getResultList().size();
    }
    
    public int getValoraciones2(Tienda miTienda) {
        return em.createQuery("SELECT v FROM Valoracion v WHERE v.tienda = :tienda AND v.puntuacion = 2")
                .setParameter("tienda", miTienda)
                .getResultList().size();
    }
    
    public int getValoraciones1(Tienda miTienda) {
        return em.createQuery("SELECT v FROM Valoracion v WHERE v.tienda = :tienda AND v.puntuacion = 1")
                .setParameter("tienda", miTienda)
                .getResultList().size();
    }

    public ValoracionFacade() {
        super(Valoracion.class);
    }
    
}
