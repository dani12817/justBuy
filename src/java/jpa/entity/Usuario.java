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
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name = "usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
    , @NamedQuery(name = "Usuario.findByNick", query = "SELECT u FROM Usuario u WHERE u.nick = :nick")
    , @NamedQuery(name = "Usuario.findByClave", query = "SELECT u FROM Usuario u WHERE u.clave = :clave")
    , @NamedQuery(name = "Usuario.findByNombre", query = "SELECT u FROM Usuario u WHERE u.nombre = :nombre")
    , @NamedQuery(name = "Usuario.findByTelefono", query = "SELECT u FROM Usuario u WHERE u.telefono = :telefono")
    , @NamedQuery(name = "Usuario.findByEmail", query = "SELECT u FROM Usuario u WHERE u.email = :email")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "nick")
    private String nick;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "clave")
    private String clave;
    @Lob
    @Column(name = "avatar")
    private byte[] avatar;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "telefono")
    private int telefono;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "email")
    private String email;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario1")
    private Collection<Tiendasfavoritas> tiendasfavoritasCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Collection<Pagopaypal> pagopaypalCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Collection<Direccion> direccionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Collection<Pagotarjeta> pagotarjetaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Collection<Valoracion> valoracionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Collection<Tienda> tiendaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Collection<Pedido> pedidoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "remitente")
    private Collection<Mensaje> mensajeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "destinatario")
    private Collection<Mensaje> mensajeCollection1;

    public Usuario() {
    }

    public Usuario(String nick) {
        this.nick = nick;
    }

    public Usuario(String nick, String clave, String nombre, int telefono, String email) {
        this.nick = nick;
        this.clave = clave;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlTransient
    public Collection<Tiendasfavoritas> getTiendasfavoritasCollection() {
        return tiendasfavoritasCollection;
    }

    public void setTiendasfavoritasCollection(Collection<Tiendasfavoritas> tiendasfavoritasCollection) {
        this.tiendasfavoritasCollection = tiendasfavoritasCollection;
    }

    @XmlTransient
    public Collection<Pagopaypal> getPagopaypalCollection() {
        return pagopaypalCollection;
    }

    public void setPagopaypalCollection(Collection<Pagopaypal> pagopaypalCollection) {
        this.pagopaypalCollection = pagopaypalCollection;
    }

    @XmlTransient
    public Collection<Direccion> getDireccionCollection() {
        return direccionCollection;
    }

    public void setDireccionCollection(Collection<Direccion> direccionCollection) {
        this.direccionCollection = direccionCollection;
    }

    @XmlTransient
    public Collection<Pagotarjeta> getPagotarjetaCollection() {
        return pagotarjetaCollection;
    }

    public void setPagotarjetaCollection(Collection<Pagotarjeta> pagotarjetaCollection) {
        this.pagotarjetaCollection = pagotarjetaCollection;
    }

    @XmlTransient
    public Collection<Valoracion> getValoracionCollection() {
        return valoracionCollection;
    }

    public void setValoracionCollection(Collection<Valoracion> valoracionCollection) {
        this.valoracionCollection = valoracionCollection;
    }

    @XmlTransient
    public Collection<Tienda> getTiendaCollection() {
        return tiendaCollection;
    }

    public void setTiendaCollection(Collection<Tienda> tiendaCollection) {
        this.tiendaCollection = tiendaCollection;
    }

    @XmlTransient
    public Collection<Pedido> getPedidoCollection() {
        return pedidoCollection;
    }

    public void setPedidoCollection(Collection<Pedido> pedidoCollection) {
        this.pedidoCollection = pedidoCollection;
    }

    @XmlTransient
    public Collection<Mensaje> getMensajeCollection() {
        return mensajeCollection;
    }

    public void setMensajeCollection(Collection<Mensaje> mensajeCollection) {
        this.mensajeCollection = mensajeCollection;
    }

    @XmlTransient
    public Collection<Mensaje> getMensajeCollection1() {
        return mensajeCollection1;
    }

    public void setMensajeCollection1(Collection<Mensaje> mensajeCollection1) {
        this.mensajeCollection1 = mensajeCollection1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nick != null ? nick.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.nick == null && other.nick != null) || (this.nick != null && !this.nick.equals(other.nick))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entity.Usuario[ nick=" + nick + " ]";
    }
    
}
