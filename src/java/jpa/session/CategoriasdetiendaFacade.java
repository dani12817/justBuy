/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.session;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpa.entity.Categoria;
import jpa.entity.Categoriasdetienda;
import jpa.entity.Tienda;

/**
 *
 * @author dleal
 */
@Stateless
public class CategoriasdetiendaFacade extends AbstractFacade<Categoriasdetienda> {

    @PersistenceContext(unitName = "justBuy-danielLealPerezPU")
    private EntityManager em;
    private List<Categoriasdetienda> listaCategoriasdeTienda;
    private Categoria[] listaCategoriasSelecionadas;
    private List<Categoriasdetienda> listaTodas;
    private boolean enLaLista;
    private String dosCategorias;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CategoriasdetiendaFacade() {
        super(Categoriasdetienda.class);
    }
    
    public List<Categoriasdetienda> findCategoriasdetiendaPorTienda(Tienda miTienda){
        return em.createQuery("SELECT c FROM Categoriasdetienda c WHERE c.tienda1 = :tienda")
                .setParameter("tienda", miTienda)
                .getResultList();
    }
    
    public String find2CategoriasPorTienda(Tienda miTienda){
        listaCategoriasdeTienda = em.createQuery("SELECT c FROM Categoriasdetienda c WHERE c.tienda1 = :tienda")
                .setParameter("tienda", miTienda)
                .getResultList();
        dosCategorias = listaCategoriasdeTienda.get(0).getCategoria1().getNombre();
        if (listaCategoriasdeTienda.size() > 1) {
            dosCategorias += ", "+listaCategoriasdeTienda.get(1).getCategoria1().getNombre();
        }
        return dosCategorias;
    }
    
    public Categoria[] findPorTienda(Tienda miTienda){
        listaCategoriasdeTienda = em.createQuery("SELECT c FROM Categoriasdetienda c WHERE c.tienda1 = :tienda")
                .setParameter("tienda", miTienda)
                .getResultList();
        
        listaCategoriasSelecionadas = new Categoria[listaCategoriasdeTienda.size()];
        for (int i = 0; i < listaCategoriasdeTienda.size(); i++) {
            listaCategoriasSelecionadas[i] = listaCategoriasdeTienda.get(i).getCategoria1();
        }
        
        return listaCategoriasSelecionadas;
    }
    
    public void editarCategoriasDeTienda(Categoria[] listaCategorias, Tienda miTienda){
        listaTodas = findCategoriasdetiendaPorTienda(miTienda);
        
        //Eliminar las categorias de tienda que ya no esten seleccionadas
        for (int i = 0; i < listaTodas.size(); i++) {
            enLaLista = false;
            System.out.println(listaTodas.size()+" - "+listaTodas.get(i).getCategoria1().getIdcategoria());
            for (Categoria listaCategoria : listaCategorias) {
                if (Objects.equals(listaTodas.get(i).getCategoria1().getIdcategoria(), listaCategoria.getIdcategoria())) {
                    enLaLista = true;
                    break;
                }
            }
            if(!enLaLista){
                remove(listaTodas.get(i));
                System.out.println("Eliminado -> "+listaTodas.get(i).getCategoria1().getNombre());
            }
        }
        
        //Añadir las nuevas categorias de tienda que esten seleccionadas
        for (Categoria listaCategoria : listaCategorias) {
            listaCategoriasdeTienda = em.createQuery("SELECT c FROM Categoriasdetienda c WHERE c.categoria1 = :categoria AND c.tienda1 = :tienda")
                    .setParameter("categoria", listaCategoria)
                    .setParameter("tienda", miTienda)
                    .getResultList();
            if (listaCategoriasdeTienda.isEmpty()) {
                System.out.println("Añadido -> " + listaCategoria.getNombre());
                create(new Categoriasdetienda(listaCategoria.getIdcategoria(), miTienda.getIdtienda()));
            }
        }
    }
    
}
