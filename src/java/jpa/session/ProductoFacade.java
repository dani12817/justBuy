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
import jpa.entity.Categoria;
import jpa.entity.Producto;
import jpa.entity.Tienda;

/**
 *
 * @author Dani
 */
@Stateless
public class ProductoFacade extends AbstractFacade<Producto> {

    @PersistenceContext(unitName = "justBuy-danielLealPerezPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductoFacade() {
        super(Producto.class);
    }
    
    public List<Producto> listaProductosTienda(Tienda tienda){
        return em.createQuery("SELECT p FROM Producto p WHERE p.tienda = :tienda ORDER BY p.categoria")
                .setParameter("tienda", tienda)
                .getResultList();
    }
    
    public List<Categoria> listaCategoriasTiendaProducto(Tienda tienda){
        return em.createQuery("SELECT c FROM Producto p, Categoria c WHERE p.tienda = :tienda AND p.categoria.idcategoria = c.idcategoria GROUP BY p.categoria")
                .setParameter("tienda", tienda)
                .getResultList();
    }
    
    public List<Producto> listaProductosTienda(Tienda tienda, String busqueda){
        return em.createQuery("SELECT p FROM Producto p WHERE p.tienda = :tienda AND p.nombre ORDER BY p.categoria LIKE '%"+busqueda+"%'")
                .setParameter("tienda", tienda)
                .getResultList();
    }
    
    public Producto productoRecienCreado(Producto producto){
        return (Producto) em.createQuery("SELECT p FROM Producto p WHERE p.tienda = :tienda AND p.nombre = :nombre AND p.categoria = :categoria "
                + "AND p.nombre = :nombre AND p.disponible = :disponible")
                .setParameter("tienda", producto.getTienda()).setParameter("nombre", producto.getNombre())
                .setParameter("categoria", producto.getCategoria()).setParameter("disponible", producto.getDisponible())
                .getResultList().get(0);
    }
    
}
