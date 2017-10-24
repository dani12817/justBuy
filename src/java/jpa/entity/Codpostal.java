/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author dleal
 */
@Entity
@Table(name = "codpostal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Codpostal.findAll", query = "SELECT c FROM Codpostal c")
    , @NamedQuery(name = "Codpostal.findByIdcodPostal", query = "SELECT c FROM Codpostal c WHERE c.idcodPostal = :idcodPostal")
    , @NamedQuery(name = "Codpostal.findByNumero", query = "SELECT c FROM Codpostal c WHERE c.numero = :numero")})
public class Codpostal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcodPostal")
    private Integer idcodPostal;
    @Size(max = 5)
    @Column(name = "numero")
    private String numero;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codPostal")
    private Collection<Direccion> direccionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codPostal")
    private Collection<Tienda> tiendaCollection;
    @JoinColumn(name = "ciudad", referencedColumnName = "idciudad")
    @ManyToOne(optional = false)
    private Ciudad ciudad;

    public Codpostal() {
    }

    public Codpostal(Integer idcodPostal) {
        this.idcodPostal = idcodPostal;
    }

    public Integer getIdcodPostal() {
        return idcodPostal;
    }

    public void setIdcodPostal(Integer idcodPostal) {
        this.idcodPostal = idcodPostal;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @XmlTransient
    public Collection<Direccion> getDireccionCollection() {
        return direccionCollection;
    }

    public void setDireccionCollection(Collection<Direccion> direccionCollection) {
        this.direccionCollection = direccionCollection;
    }

    @XmlTransient
    public Collection<Tienda> getTiendaCollection() {
        return tiendaCollection;
    }

    public void setTiendaCollection(Collection<Tienda> tiendaCollection) {
        this.tiendaCollection = tiendaCollection;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcodPostal != null ? idcodPostal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Codpostal)) {
            return false;
        }
        Codpostal other = (Codpostal) object;
        if ((this.idcodPostal == null && other.idcodPostal != null) || (this.idcodPostal != null && !this.idcodPostal.equals(other.idcodPostal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entity.Codpostal[ idcodPostal=" + idcodPostal + " ]";
    }
    
}
