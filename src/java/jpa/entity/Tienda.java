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
import javax.persistence.Lob;
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
@Table(name = "tienda")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tienda.findAll", query = "SELECT t FROM Tienda t")
    , @NamedQuery(name = "Tienda.findByIdtienda", query = "SELECT t FROM Tienda t WHERE t.idtienda = :idtienda")
    , @NamedQuery(name = "Tienda.findByNombre", query = "SELECT t FROM Tienda t WHERE t.nombre = :nombre")
    , @NamedQuery(name = "Tienda.findByDescripcion", query = "SELECT t FROM Tienda t WHERE t.descripcion = :descripcion")
    , @NamedQuery(name = "Tienda.findByTelefono", query = "SELECT t FROM Tienda t WHERE t.telefono = :telefono")
    , @NamedQuery(name = "Tienda.findByLatitud", query = "SELECT t FROM Tienda t WHERE t.latitud = :latitud")
    , @NamedQuery(name = "Tienda.findByLongitud", query = "SELECT t FROM Tienda t WHERE t.longitud = :longitud")
    , @NamedQuery(name = "Tienda.findByTiendaActiva", query = "SELECT t FROM Tienda t WHERE t.tiendaActiva = :tiendaActiva")
    , @NamedQuery(name = "Tienda.findByTipoTienda", query = "SELECT t FROM Tienda t WHERE t.tipoTienda = :tipoTienda")
    , @NamedQuery(name = "Tienda.findByDireccionLinea1", query = "SELECT t FROM Tienda t WHERE t.direccionLinea1 = :direccionLinea1")
    , @NamedQuery(name = "Tienda.findByDireccionLinea2", query = "SELECT t FROM Tienda t WHERE t.direccionLinea2 = :direccionLinea2")})
public class Tienda implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtienda")
    private Integer idtienda;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombre")
    private String nombre;
    @Lob
    @Column(name = "logo")
    private byte[] logo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "telefono")
    private int telefono;
    @Basic(optional = false)
    @NotNull
    @Column(name = "latitud")
    private double latitud;
    @Basic(optional = false)
    @NotNull
    @Column(name = "longitud")
    private double longitud;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tiendaActiva")
    private short tiendaActiva;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tipoTienda")
    private int tipoTienda;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "direccionLinea1")
    private String direccionLinea1;
    @Size(max = 45)
    @Column(name = "direccionLinea2")
    private String direccionLinea2;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tienda")
    private Collection<Horariotienda> horariotiendaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tienda1")
    private Collection<Tiendasfavoritas> tiendasfavoritasCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tienda")
    private Collection<Valoracion> valoracionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tienda")
    private Collection<Producto> productoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tienda")
    private Collection<Envio> envioCollection;
    @JoinColumn(name = "codPostal", referencedColumnName = "idcodPostal")
    @ManyToOne(optional = false)
    private Codpostal codPostal;
    @JoinColumn(name = "usuario", referencedColumnName = "nick")
    @ManyToOne(optional = false)
    private Usuario usuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tienda1")
    private Collection<Categoriasdetienda> categoriasdetiendaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tienda")
    private Collection<Pedido> pedidoCollection;

    public Tienda() {
    }

    public Tienda(Integer idtienda) {
        this.idtienda = idtienda;
    }

    public Tienda(Integer idtienda, String nombre, String descripcion, int telefono, double latitud, double longitud,
            short tiendaActiva, int tipoTienda, String direccionLinea1, String direccionLinea2) {
        this.idtienda = idtienda;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.telefono = telefono;
        this.latitud = latitud;
        this.longitud = longitud;
        this.tiendaActiva = tiendaActiva;
        this.tipoTienda = tipoTienda;
        this.direccionLinea1 = direccionLinea1;
        this.direccionLinea2 = direccionLinea2;
    }

    public Tienda(Integer idtienda, String nombre, String descripcion, int telefono, double latitud, double longitud, short tiendaActiva, int tipoTienda, String direccionLinea1) {
        this.idtienda = idtienda;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.telefono = telefono;
        this.latitud = latitud;
        this.longitud = longitud;
        this.tiendaActiva = tiendaActiva;
        this.tipoTienda = tipoTienda;
        this.direccionLinea1 = direccionLinea1;
    }

    public Integer getIdtienda() {
        return idtienda;
    }

    public void setIdtienda(Integer idtienda) {
        this.idtienda = idtienda;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public short getTiendaActiva() {
        return tiendaActiva;
    }

    public void setTiendaActiva(short tiendaActiva) {
        this.tiendaActiva = tiendaActiva;
    }

    public int getTipoTienda() {
        return tipoTienda;
    }

    public void setTipoTienda(int tipoTienda) {
        this.tipoTienda = tipoTienda;
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

    @XmlTransient
    public Collection<Horariotienda> getHorariotiendaCollection() {
        return horariotiendaCollection;
    }

    public void setHorariotiendaCollection(Collection<Horariotienda> horariotiendaCollection) {
        this.horariotiendaCollection = horariotiendaCollection;
    }

    @XmlTransient
    public Collection<Tiendasfavoritas> getTiendasfavoritasCollection() {
        return tiendasfavoritasCollection;
    }

    public void setTiendasfavoritasCollection(Collection<Tiendasfavoritas> tiendasfavoritasCollection) {
        this.tiendasfavoritasCollection = tiendasfavoritasCollection;
    }

    @XmlTransient
    public Collection<Valoracion> getValoracionCollection() {
        return valoracionCollection;
    }

    public void setValoracionCollection(Collection<Valoracion> valoracionCollection) {
        this.valoracionCollection = valoracionCollection;
    }

    @XmlTransient
    public Collection<Producto> getProductoCollection() {
        return productoCollection;
    }

    public void setProductoCollection(Collection<Producto> productoCollection) {
        this.productoCollection = productoCollection;
    }

    @XmlTransient
    public Collection<Envio> getEnvioCollection() {
        return envioCollection;
    }

    public void setEnvioCollection(Collection<Envio> envioCollection) {
        this.envioCollection = envioCollection;
    }

    public Codpostal getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(Codpostal codPostal) {
        this.codPostal = codPostal;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @XmlTransient
    public Collection<Categoriasdetienda> getCategoriasdetiendaCollection() {
        return categoriasdetiendaCollection;
    }

    public void setCategoriasdetiendaCollection(Collection<Categoriasdetienda> categoriasdetiendaCollection) {
        this.categoriasdetiendaCollection = categoriasdetiendaCollection;
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
        hash += (idtienda != null ? idtienda.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tienda)) {
            return false;
        }
        Tienda other = (Tienda) object;
        if ((this.idtienda == null && other.idtienda != null) || (this.idtienda != null && !this.idtienda.equals(other.idtienda))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entity.Tienda[ idtienda=" + idtienda + " ]";
    }
    
}
