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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dleal
 */
@Entity
@Table(name = "lineapedido")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lineapedido.findAll", query = "SELECT l FROM Lineapedido l")
    , @NamedQuery(name = "Lineapedido.findByIdlineaPedido", query = "SELECT l FROM Lineapedido l WHERE l.idlineaPedido = :idlineaPedido")
    , @NamedQuery(name = "Lineapedido.findByCantidad", query = "SELECT l FROM Lineapedido l WHERE l.cantidad = :cantidad")})
public class Lineapedido implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idlineaPedido")
    private Integer idlineaPedido;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidad")
    private int cantidad;
    @JoinColumn(name = "pedido", referencedColumnName = "idpedido")
    @ManyToOne(optional = false)
    private Pedido pedido;
    @JoinColumn(name = "producto", referencedColumnName = "idproducto")
    @ManyToOne(optional = false)
    private Producto producto;

    public Lineapedido() {
    }

    public Lineapedido(Integer idlineaPedido) {
        this.idlineaPedido = idlineaPedido;
    }

    public Lineapedido(Integer idlineaPedido, int cantidad) {
        this.idlineaPedido = idlineaPedido;
        this.cantidad = cantidad;
    }

    public Lineapedido(Pedido pedido, Producto producto, int cantidad) {
        this.pedido = pedido;
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Integer getIdlineaPedido() {
        return idlineaPedido;
    }

    public void setIdlineaPedido(Integer idlineaPedido) {
        this.idlineaPedido = idlineaPedido;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idlineaPedido != null ? idlineaPedido.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lineapedido)) {
            return false;
        }
        Lineapedido other = (Lineapedido) object;
        if ((this.idlineaPedido == null && other.idlineaPedido != null) || (this.idlineaPedido != null && !this.idlineaPedido.equals(other.idlineaPedido))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entity.Lineapedido[ idlineaPedido=" + idlineaPedido + " ]";
    }
    
}
