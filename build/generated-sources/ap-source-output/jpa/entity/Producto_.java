package jpa.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import jpa.entity.Categoria;
import jpa.entity.Lineapedido;
import jpa.entity.Mensaje;
import jpa.entity.Tienda;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-23T13:36:49")
@StaticMetamodel(Producto.class)
public class Producto_ { 

    public static volatile SingularAttribute<Producto, String> descripcion;
    public static volatile CollectionAttribute<Producto, Lineapedido> lineapedidoCollection;
    public static volatile CollectionAttribute<Producto, Mensaje> mensajeCollection;
    public static volatile SingularAttribute<Producto, Double> precio;
    public static volatile SingularAttribute<Producto, Tienda> tienda;
    public static volatile SingularAttribute<Producto, Categoria> categoria;
    public static volatile SingularAttribute<Producto, byte[]> imagen;
    public static volatile SingularAttribute<Producto, Integer> cantidad;
    public static volatile SingularAttribute<Producto, String> nombre;
    public static volatile SingularAttribute<Producto, Integer> idproducto;
    public static volatile SingularAttribute<Producto, Short> disponible;

}