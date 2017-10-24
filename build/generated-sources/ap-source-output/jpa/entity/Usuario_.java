package jpa.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import jpa.entity.Direccion;
import jpa.entity.Mensaje;
import jpa.entity.Pagopaypal;
import jpa.entity.Pagotarjeta;
import jpa.entity.Pedido;
import jpa.entity.Tienda;
import jpa.entity.Tiendasfavoritas;
import jpa.entity.Valoracion;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-23T13:36:49")
@StaticMetamodel(Usuario.class)
public class Usuario_ { 

    public static volatile CollectionAttribute<Usuario, Tienda> tiendaCollection;
    public static volatile SingularAttribute<Usuario, String> clave;
    public static volatile CollectionAttribute<Usuario, Mensaje> mensajeCollection;
    public static volatile CollectionAttribute<Usuario, Pagotarjeta> pagotarjetaCollection;
    public static volatile CollectionAttribute<Usuario, Valoracion> valoracionCollection;
    public static volatile SingularAttribute<Usuario, byte[]> avatar;
    public static volatile CollectionAttribute<Usuario, Direccion> direccionCollection;
    public static volatile SingularAttribute<Usuario, String> nombre;
    public static volatile CollectionAttribute<Usuario, Pagopaypal> pagopaypalCollection;
    public static volatile SingularAttribute<Usuario, String> nick;
    public static volatile CollectionAttribute<Usuario, Tiendasfavoritas> tiendasfavoritasCollection;
    public static volatile CollectionAttribute<Usuario, Pedido> pedidoCollection;
    public static volatile SingularAttribute<Usuario, Integer> telefono;
    public static volatile SingularAttribute<Usuario, String> email;
    public static volatile CollectionAttribute<Usuario, Mensaje> mensajeCollection1;

}