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
@Table(name = "pagopaypal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pagopaypal.findAll", query = "SELECT p FROM Pagopaypal p")
    , @NamedQuery(name = "Pagopaypal.findByIdpagoPaypal", query = "SELECT p FROM Pagopaypal p WHERE p.idpagoPaypal = :idpagoPaypal")
    , @NamedQuery(name = "Pagopaypal.findByTitular", query = "SELECT p FROM Pagopaypal p WHERE p.titular = :titular")
    , @NamedQuery(name = "Pagopaypal.findByMail", query = "SELECT p FROM Pagopaypal p WHERE p.mail = :mail")})
public class Pagopaypal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpagoPaypal")
    private Integer idpagoPaypal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "titular")
    private String titular;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "mail")
    private String mail;
    @JoinColumn(name = "usuario", referencedColumnName = "nick")
    @ManyToOne(optional = false)
    private Usuario usuario;

    public Pagopaypal() {
    }

    public Pagopaypal(Integer idpagoPaypal) {
        this.idpagoPaypal = idpagoPaypal;
    }

    public Pagopaypal(Integer idpagoPaypal, String titular, String mail) {
        this.idpagoPaypal = idpagoPaypal;
        this.titular = titular;
        this.mail = mail;
    }

    public Integer getIdpagoPaypal() {
        return idpagoPaypal;
    }

    public void setIdpagoPaypal(Integer idpagoPaypal) {
        this.idpagoPaypal = idpagoPaypal;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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
        hash += (idpagoPaypal != null ? idpagoPaypal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pagopaypal)) {
            return false;
        }
        Pagopaypal other = (Pagopaypal) object;
        if ((this.idpagoPaypal == null && other.idpagoPaypal != null) || (this.idpagoPaypal != null && !this.idpagoPaypal.equals(other.idpagoPaypal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entity.Pagopaypal[ idpagoPaypal=" + idpagoPaypal + " ]";
    }
    
}
