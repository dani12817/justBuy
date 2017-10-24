/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dleal
 */
@Entity
@Table(name = "horariotienda")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Horariotienda.findAll", query = "SELECT h FROM Horariotienda h")
    , @NamedQuery(name = "Horariotienda.findByIdhorarioTienda", query = "SELECT h FROM Horariotienda h WHERE h.idhorarioTienda = :idhorarioTienda")
    , @NamedQuery(name = "Horariotienda.findByLunes", query = "SELECT h FROM Horariotienda h WHERE h.lunes = :lunes")
    , @NamedQuery(name = "Horariotienda.findByMartes", query = "SELECT h FROM Horariotienda h WHERE h.martes = :martes")
    , @NamedQuery(name = "Horariotienda.findByMiercoles", query = "SELECT h FROM Horariotienda h WHERE h.miercoles = :miercoles")
    , @NamedQuery(name = "Horariotienda.findByJueves", query = "SELECT h FROM Horariotienda h WHERE h.jueves = :jueves")
    , @NamedQuery(name = "Horariotienda.findByViernes", query = "SELECT h FROM Horariotienda h WHERE h.viernes = :viernes")
    , @NamedQuery(name = "Horariotienda.findBySabado", query = "SELECT h FROM Horariotienda h WHERE h.sabado = :sabado")
    , @NamedQuery(name = "Horariotienda.findByDomingo", query = "SELECT h FROM Horariotienda h WHERE h.domingo = :domingo")})
public class Horariotienda implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idhorarioTienda")
    private Integer idhorarioTienda;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "lunes")
    private String lunes;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "martes")
    private String martes;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "miercoles")
    private String miercoles;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "jueves")
    private String jueves;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "viernes")
    private String viernes;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "sabado")
    private String sabado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "domingo")
    private String domingo;
    @JoinColumn(name = "tienda", referencedColumnName = "idtienda")
    @ManyToOne(optional = false)
    private Tienda tienda;

    public Horariotienda() {
    }

    public Horariotienda(Integer idhorarioTienda) {
        this.idhorarioTienda = idhorarioTienda;
    }

    public Horariotienda(Integer idhorarioTienda, String lunes, String martes, String miercoles, String jueves, String viernes, String sabado, String domingo) {
        this.idhorarioTienda = idhorarioTienda;
        this.lunes = lunes;
        this.martes = martes;
        this.miercoles = miercoles;
        this.jueves = jueves;
        this.viernes = viernes;
        this.sabado = sabado;
        this.domingo = domingo;
    }

    public Integer getIdhorarioTienda() {
        return idhorarioTienda;
    }

    public void setIdhorarioTienda(Integer idhorarioTienda) {
        this.idhorarioTienda = idhorarioTienda;
    }

    public String getLunes() {
        return lunes;
    }

    public void setLunes(String lunes) {
        this.lunes = lunes;
    }

    public String getMartes() {
        return martes;
    }

    public void setMartes(String martes) {
        this.martes = martes;
    }

    public String getMiercoles() {
        return miercoles;
    }

    public void setMiercoles(String miercoles) {
        this.miercoles = miercoles;
    }

    public String getJueves() {
        return jueves;
    }

    public void setJueves(String jueves) {
        this.jueves = jueves;
    }

    public String getViernes() {
        return viernes;
    }

    public void setViernes(String viernes) {
        this.viernes = viernes;
    }

    public String getSabado() {
        return sabado;
    }

    public void setSabado(String sabado) {
        this.sabado = sabado;
    }

    public String getDomingo() {
        return domingo;
    }

    public void setDomingo(String domingo) {
        this.domingo = domingo;
    }

    public Tienda getTienda() {
        return tienda;
    }

    public void setTienda(Tienda tienda) {
        this.tienda = tienda;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idhorarioTienda != null ? idhorarioTienda.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Horariotienda)) {
            return false;
        }
        Horariotienda other = (Horariotienda) object;
        if ((this.idhorarioTienda == null && other.idhorarioTienda != null) || (this.idhorarioTienda != null && !this.idhorarioTienda.equals(other.idhorarioTienda))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entity.Horariotienda[ idhorarioTienda=" + idhorarioTienda + " ]";
    }
    
}
