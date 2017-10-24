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
@Table(name = "envio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Envio.findAll", query = "SELECT e FROM Envio e")
    , @NamedQuery(name = "Envio.findByIdenvio", query = "SELECT e FROM Envio e WHERE e.idenvio = :idenvio")
    , @NamedQuery(name = "Envio.findByNombre", query = "SELECT e FROM Envio e WHERE e.nombre = :nombre")
    , @NamedQuery(name = "Envio.findByPrecioEnvio", query = "SELECT e FROM Envio e WHERE e.precioEnvio = :precioEnvio")})
public class Envio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idenvio")
    private Integer idenvio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "precioEnvio")
    private double precioEnvio;
    @JoinColumn(name = "tienda", referencedColumnName = "idtienda")
    @ManyToOne(optional = false)
    private Tienda tienda;

    public Envio() {
    }

    public Envio(Integer idenvio) {
        this.idenvio = idenvio;
    }

    public Envio(Integer idenvio, String nombre, double precioEnvio) {
        this.idenvio = idenvio;
        this.nombre = nombre;
        this.precioEnvio = precioEnvio;
    }

    public Integer getIdenvio() {
        return idenvio;
    }

    public void setIdenvio(Integer idenvio) {
        this.idenvio = idenvio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecioEnvio() {
        return precioEnvio;
    }

    public void setPrecioEnvio(double precioEnvio) {
        this.precioEnvio = precioEnvio;
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
        hash += (idenvio != null ? idenvio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Envio)) {
            return false;
        }
        Envio other = (Envio) object;
        if ((this.idenvio == null && other.idenvio != null) || (this.idenvio != null && !this.idenvio.equals(other.idenvio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entity.Envio[ idenvio=" + idenvio + " ]";
    }
    
}
