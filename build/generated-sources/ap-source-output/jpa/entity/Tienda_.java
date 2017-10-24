package jpa.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import jpa.entity.Categoriasdetienda;
import jpa.entity.Codpostal;
import jpa.entity.Envio;
import jpa.entity.Horariotienda;
import jpa.entity.Pedido;
import jpa.entity.Producto;
import jpa.entity.Tiendasfavoritas;
import jpa.entity.Usuario;
import jpa.entity.Valoracion;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-23T13:36:49")
@StaticMetamodel(Tienda.class)
public class Tienda_ { 

    public static volatile SingularAttribute<Tienda, String> descripcion;
    public static volatile CollectionAttribute<Tienda, Envio> envioCollection;
    public static volatile SingularAttribute<Tienda, Double> latitud;
    public static volatile SingularAttribute<Tienda, Integer> idtienda;
    public static volatile CollectionAttribute<Tienda, Valoracion> valoracionCollection;
    public static volatile CollectionAttribute<Tienda, Categoriasdetienda> categoriasdetiendaCollection;
    public static volatile SingularAttribute<Tienda, String> nombre;
    public static volatile SingularAttribute<Tienda, Short> tiendaActiva;
    public static volatile SingularAttribute<Tienda, Integer> tipoTienda;
    public static volatile CollectionAttribute<Tienda, Tiendasfavoritas> tiendasfavoritasCollection;
    public static volatile SingularAttribute<Tienda, Double> longitud;
    public static volatile SingularAttribute<Tienda, String> direccionLinea2;
    public static volatile SingularAttribute<Tienda, String> direccionLinea1;
    public static volatile SingularAttribute<Tienda, byte[]> logo;
    public static volatile SingularAttribute<Tienda, Usuario> usuario;
    public static volatile CollectionAttribute<Tienda, Producto> productoCollection;
    public static volatile CollectionAttribute<Tienda, Pedido> pedidoCollection;
    public static volatile SingularAttribute<Tienda, Integer> telefono;
    public static volatile CollectionAttribute<Tienda, Horariotienda> horariotiendaCollection;
    public static volatile SingularAttribute<Tienda, Codpostal> codPostal;

}