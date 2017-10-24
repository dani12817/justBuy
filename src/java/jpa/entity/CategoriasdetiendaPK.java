/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author dleal
 */
@Embeddable
public class CategoriasdetiendaPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "categoria")
    private int categoria;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tienda")
    private int tienda;

    public CategoriasdetiendaPK() {
    }

    public CategoriasdetiendaPK(int categoria, int tienda) {
        this.categoria = categoria;
        this.tienda = tienda;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public int getTienda() {
        return tienda;
    }

    public void setTienda(int tienda) {
        this.tienda = tienda;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) categoria;
        hash += (int) tienda;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CategoriasdetiendaPK)) {
            return false;
        }
        CategoriasdetiendaPK other = (CategoriasdetiendaPK) object;
        if (this.categoria != other.categoria) {
            return false;
        }
        if (this.tienda != other.tienda) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entity.CategoriasdetiendaPK[ categoria=" + categoria + ", tienda=" + tienda + " ]";
    }
    
}
