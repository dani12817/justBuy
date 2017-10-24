/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dleal
 */
@Entity
@Table(name = "valoracion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Valoracion.findAll", query = "SELECT v FROM Valoracion v")
    , @NamedQuery(name = "Valoracion.findByIdvaloracion", query = "SELECT v FROM Valoracion v WHERE v.idvaloracion = :idvaloracion")
    , @NamedQuery(name = "Valoracion.findByTexto", query = "SELECT v FROM Valoracion v WHERE v.texto = :texto")
    , @NamedQuery(name = "Valoracion.findByPuntuacion", query = "SELECT v FROM Valoracion v WHERE v.puntuacion = :puntuacion")
    , @NamedQuery(name = "Valoracion.findByFecha", query = "SELECT v FROM Valoracion v WHERE v.fecha = :fecha")})
public class Valoracion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idvaloracion")
    private Integer idvaloracion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "texto")
    private String texto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "puntuacion")
    private int puntuacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @JoinColumn(name = "tienda", referencedColumnName = "idtienda")
    @ManyToOne(optional = false)
    private Tienda tienda;
    @JoinColumn(name = "usuario", referencedColumnName = "nick")
    @ManyToOne(optional = false)
    private Usuario usuario;

    public Valoracion() {
    }

    public Valoracion(Integer idvaloracion) {
        this.idvaloracion = idvaloracion;
    }

    public Valoracion(Integer idvaloracion, String texto, int puntuacion, Date fecha) {
        this.idvaloracion = idvaloracion;
        this.texto = texto;
        this.puntuacion = puntuacion;
        this.fecha = fecha;
    }

    public Integer getIdvaloracion() {
        return idvaloracion;
    }

    public void setIdvaloracion(Integer idvaloracion) {
        this.idvaloracion = idvaloracion;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getFechaConFormato() {
        return new SimpleDateFormat("dd/MM/yyyy").format(fecha);
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Tienda getTienda() {
        return tienda;
    }

    public void setTienda(Tienda tienda) {
        this.tienda = tienda;
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
        hash += (idvaloracion != null ? idvaloracion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Valoracion)) {
            return false;
        }
        Valoracion other = (Valoracion) object;
        if ((this.idvaloracion == null && other.idvaloracion != null) || (this.idvaloracion != null && !this.idvaloracion.equals(other.idvaloracion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entity.Valoracion[ idvaloracion=" + idvaloracion + " ]";
    }
    
}
