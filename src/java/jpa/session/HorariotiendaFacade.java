/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.session;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpa.entity.Horariotienda;
import jpa.entity.Tienda;

/**
 *
 * @author dleal
 */
@Stateless
public class HorariotiendaFacade extends AbstractFacade<Horariotienda> {

    @PersistenceContext(unitName = "justBuy-danielLealPerezPU")
    private EntityManager em;
    private List<Horariotienda> horarioTiendaList;
    private Horariotienda horariotienda;
    private Date d;
    private int diaHoy;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HorariotiendaFacade() {
        super(Horariotienda.class);
    }
    
    public Horariotienda findPorTienda(Tienda tienda){
        horarioTiendaList = em.createQuery("SELECT h FROM Horariotienda h WHERE h.tienda = :tienda")
                .setParameter("tienda", tienda)
                .getResultList();
        
        if(!horarioTiendaList.isEmpty()){
            return horarioTiendaList.get(0);
        }
        
        return null;
    }

    public String findDiaDeHoyTienda(Tienda tienda) {
        horarioTiendaList = em.createQuery("SELECT h FROM Horariotienda h WHERE h.tienda = :tienda")
                .setParameter("tienda", tienda)
                .getResultList();
        if(horarioTiendaList.isEmpty()){
            return "Sin Horario";
        }
        
        d = new Date();
        diaHoy = d.getDay();
        
        horariotienda = horarioTiendaList.get(0);
        
        switch(diaHoy){
            case 1:
                return horariotienda.getLunes();
            case 2:
                return horariotienda.getMartes();
            case 3:
                return horariotienda.getMiercoles();
            case 4:
                return horariotienda.getJueves();
            case 5:
                return horariotienda.getViernes();
            case 6:
                return horariotienda.getSabado();
            default:
                return horariotienda.getDomingo();
                
        }
    }
    
}
