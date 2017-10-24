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
@Table(name = "tiendasfavoritas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tiendasfavoritas.findAll", query = "SELECT t FROM Tiendasfavoritas t")
    , @NamedQuery(name = "Tiendasfavoritas.findByUsuario", query = "SELECT t FROM Tiendasfavoritas t WHERE t.tiendasfavoritasPK.usuario = :usuario")
    , @NamedQuery(name = "Tiendasfavoritas.findByTienda", query = "SELECT t FROM Tiendasfavoritas t WHERE t.tiendasfavoritasPK.tienda = :tienda")
    , @NamedQuery(name = "Tiendasfavoritas.findByTiendasFavoritasBoolean", query = "SELECT t FROM Tiendasfavoritas t WHERE t.tiendasFavoritasBoolean = :tiendasFavoritasBoolean")})
public class Tiendasfavoritas implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TiendasfavoritasPK tiendasfavoritasPK;
    @Column(name = "tiendasFavoritasBoolean")
    private Short tiendasFavoritasBoolean;
    @JoinColumn(name = "tienda", referencedColumnName = "idtienda", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Tienda tienda1;
    @JoinColumn(name = "usuario", referencedColumnName = "nick", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario1;

    public Tiendasfavoritas() {
    }

    public Tiendasfavoritas(TiendasfavoritasPK tiendasfavoritasPK) {
        this.tiendasfavoritasPK = tiendasfavoritasPK;
    }

    public Tiendasfavoritas(String usuario, int tienda) {
        this.tiendasfavoritasPK = new TiendasfavoritasPK(usuario, tienda);
    }

    public TiendasfavoritasPK getTiendasfavoritasPK() {
        return tiendasfavoritasPK;
    }

    public void setTiendasfavoritasPK(TiendasfavoritasPK tiendasfavoritasPK) {
        this.tiendasfavoritasPK = tiendasfavoritasPK;
    }

    public Short getTiendasFavoritasBoolean() {
        return tiendasFavoritasBoolean;
    }

    public void setTiendasFavoritasBoolean(Short tiendasFavoritasBoolean) {
        this.tiendasFavoritasBoolean = tiendasFavoritasBoolean;
    }

    public Tienda getTienda1() {
        return tienda1;
    }

    public void setTienda1(Tienda tienda1) {
        this.tienda1 = tienda1;
    }

    public Usuario getUsuario1() {
        return usuario1;
    }

    public void setUsuario1(Usuario usuario1) {
        this.usuario1 = usuario1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tiendasfavoritasPK != null ? tiendasfavoritasPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tiendasfavoritas)) {
            return false;
        }
        Tiendasfavoritas other = (Tiendasfavoritas) object;
        if ((this.tiendasfavoritasPK == null && other.tiendasfavoritasPK != null) || (this.tiendasfavoritasPK != null && !this.tiendasfavoritasPK.equals(other.tiendasfavoritasPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entity.Tiendasfavoritas[ tiendasfavoritasPK=" + tiendasfavoritasPK + " ]";
    }
    
}
