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
@Table(name = "pagotarjeta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pagotarjeta.findAll", query = "SELECT p FROM Pagotarjeta p")
    , @NamedQuery(name = "Pagotarjeta.findByIdmetodoPago", query = "SELECT p FROM Pagotarjeta p WHERE p.idmetodoPago = :idmetodoPago")
    , @NamedQuery(name = "Pagotarjeta.findByTitular", query = "SELECT p FROM Pagotarjeta p WHERE p.titular = :titular")
    , @NamedQuery(name = "Pagotarjeta.findByNumeroTarjeta", query = "SELECT p FROM Pagotarjeta p WHERE p.numeroTarjeta = :numeroTarjeta")
    , @NamedQuery(name = "Pagotarjeta.findByCaduca", query = "SELECT p FROM Pagotarjeta p WHERE p.caduca = :caduca")})
public class Pagotarjeta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idmetodoPago")
    private Integer idmetodoPago;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "titular")
    private String titular;
    @Basic(optional = false)
    @NotNull
    @Column(name = "numeroTarjeta")
    private int numeroTarjeta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "caduca")
    private String caduca;
    @JoinColumn(name = "usuario", referencedColumnName = "nick")
    @ManyToOne(optional = false)
    private Usuario usuario;

    public Pagotarjeta() {
    }

    public Pagotarjeta(Integer idmetodoPago) {
        this.idmetodoPago = idmetodoPago;
    }

    public Pagotarjeta(Integer idmetodoPago, String titular, int numeroTarjeta, String caduca) {
        this.idmetodoPago = idmetodoPago;
        this.titular = titular;
        this.numeroTarjeta = numeroTarjeta;
        this.caduca = caduca;
    }

    public Integer getIdmetodoPago() {
        return idmetodoPago;
    }

    public void setIdmetodoPago(Integer idmetodoPago) {
        this.idmetodoPago = idmetodoPago;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public int getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(int numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getCaduca() {
        return caduca;
    }

    public void setCaduca(String caduca) {
        this.caduca = caduca;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmetodoPago != null ? idmetodoPago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pagotarjeta)) {
            return false;
        }
        Pagotarjeta other = (Pagotarjeta) object;
        if ((this.idmetodoPago == null && other.idmetodoPago != null) || (this.idmetodoPago != null && !this.idmetodoPago.equals(other.idmetodoPago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entity.Pagotarjeta[ idmetodoPago=" + idmetodoPago + " ]";
    }
    
}
