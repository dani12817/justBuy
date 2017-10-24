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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author dleal
 */
@Entity
@Table(name = "direccion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Direccion.findAll", query = "SELECT d FROM Direccion d")
    , @NamedQuery(name = "Direccion.findByIddireccion", query = "SELECT d FROM Direccion d WHERE d.iddireccion = :iddireccion")
    , @NamedQuery(name = "Direccion.findByNombre", query = "SELECT d FROM Direccion d WHERE d.nombre = :nombre")
    , @NamedQuery(name = "Direccion.findByDireccionLinea1", query = "SELECT d FROM Direccion d WHERE d.direccionLinea1 = :direccionLinea1")
    , @NamedQuery(name = "Direccion.findByDireccionLinea2", query = "SELECT d FROM Direccion d WHERE d.direccionLinea2 = :direccionLinea2")})
public class Direccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iddireccion")
    private Integer iddireccion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "direccionLinea1")
    private String direccionLinea1;
    @Size(max = 40)
    @Column(name = "direccionLinea2")
    private String direccionLinea2;
    @JoinColumn(name = "codPostal", referencedColumnName = "idcodPostal")
    @ManyToOne(optional = false)
    private Codpostal codPostal;
    @JoinColumn(name = "usuario", referencedColumnName = "nick")
    @ManyToOne(optional = false)
    private Usuario usuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "direccion")
    private Collection<Pedido> pedidoCollection;

    public Direccion() {
    }

    public Direccion(Integer iddireccion) {
        this.iddireccion = iddireccion;
    }

    public Direccion(Integer iddireccion, String nombre, String direccionLinea1) {
        this.iddireccion = iddireccion;
        this.nombre = nombre;
        this.direccionLinea1 = direccionLinea1;
    }

    public Integer getIddireccion() {
        return iddireccion;
    }

    public void setIddireccion(Integer iddireccion) {
        this.iddireccion = iddireccion;
    }
    
    public String getDireccion(){
        return direccionLinea1+" "+direccionLinea2;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccionLinea1() {
        return direccionLinea1;
    }

    public void setDireccionLinea1(String direccionLinea1) {
        this.direccionLinea1 = direccionLinea1;
    }

    public String getDireccionLinea2() {
        return direccionLinea2;
    }

    public void setDireccionLinea2(String direccionLinea2) {
        this.direccionLinea2 = direccionLinea2;
    }

    public Codpostal getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(Codpostal codPostal) {
        this.codPostal = codPostal;
    }
    
    public Ciudad getCiudad(){
        return codPostal.getCiudad();
    }
    
    public Provincia getProvincia(){
        return codPostal.getCiudad().getProvincia();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @XmlTransient
    public Collection<Pedido> getPedidoCollection() {
        return pedidoCollection;
    }

    public void setPedidoCollection(Collection<Pedido> pedidoCollection) {
        this.pedidoCollection = pedidoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddireccion != null ? iddireccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Direccion)) {
            return false;
        }
        Direccion other = (Direccion) object;
        if ((this.iddireccion == null && other.iddireccion != null) || (this.iddireccion != null && !this.iddireccion.equals(other.iddireccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entity.Direccion[ iddireccion=" + iddireccion + " ]";
    }
    
}
