/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dleal
 */
@Entity
@Table(name = "categoriasdetienda")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Categoriasdetienda.findAll", query = "SELECT c FROM Categoriasdetienda c")
    , @NamedQuery(name = "Categoriasdetienda.findByCategoria", query = "SELECT c FROM Categoriasdetienda c WHERE c.categoriasdetiendaPK.categoria = :categoria")
    , @NamedQuery(name = "Categoriasdetienda.findByTienda", query = "SELECT c FROM Categoriasdetienda c WHERE c.categoriasdetiendaPK.tienda = :tienda")
    , @NamedQuery(name = "Categoriasdetienda.findByCategoriasDeTiendaBoolean", query = "SELECT c FROM Categoriasdetienda c WHERE c.categoriasDeTiendaBoolean = :categoriasDeTiendaBoolean")})
public class Categoriasdetienda implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CategoriasdetiendaPK categoriasdetiendaPK;
    @Column(name = "categoriasDeTiendaBoolean")
    private Short categoriasDeTiendaBoolean;
    @JoinColumn(name = "categoria", referencedColumnName = "idcategoria", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Categoria categoria1;
    @JoinColumn(name = "tienda", referencedColumnName = "idtienda", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Tienda tienda1;

    public Categoriasdetienda() {
    }

    public Categoriasdetienda(CategoriasdetiendaPK categoriasdetiendaPK) {
        this.categoriasdetiendaPK = categoriasdetiendaPK;
    }

    public Categoriasdetienda(int categoria, int tienda) {
        this.categoriasdetiendaPK = new CategoriasdetiendaPK(categoria, tienda);
    }

    public CategoriasdetiendaPK getCategoriasdetiendaPK() {
        return categoriasdetiendaPK;
    }

    public void setCategoriasdetiendaPK(CategoriasdetiendaPK categoriasdetiendaPK) {
        this.categoriasdetiendaPK = categoriasdetiendaPK;
    }

    public Short getCategoriasDeTiendaBoolean() {
        return categoriasDeTiendaBoolean;
    }

    public void setCategoriasDeTiendaBoolean(Short categoriasDeTiendaBoolean) {
        this.categoriasDeTiendaBoolean = categoriasDeTiendaBoolean;
    }

    public Categoria getCategoria1() {
        return categoria1;
    }

    public void setCategoria1(Categoria categoria1) {
        this.categoria1 = categoria1;
    }

    public Tienda getTienda1() {
        return tienda1;
    }

    public void setTienda1(Tienda tienda1) {
        this.tienda1 = tienda1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (categoriasdetiendaPK != null ? categoriasdetiendaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Categoriasdetienda)) {
            return false;
        }
        Categoriasdetienda other = (Categoriasdetienda) object;
        if ((this.categoriasdetiendaPK == null && other.categoriasdetiendaPK != null) || (this.categoriasdetiendaPK != null && !this.categoriasdetiendaPK.equals(other.categoriasdetiendaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entity.Categoriasdetienda[ categoriasdetiendaPK=" + categoriasdetiendaPK + " ]";
    }
    
}
