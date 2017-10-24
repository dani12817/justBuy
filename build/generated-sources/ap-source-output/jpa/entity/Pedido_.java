package jpa.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import jpa.entity.Direccion;
import jpa.entity.Lineapedido;
import jpa.entity.Tienda;
import jpa.entity.Usuario;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-23T13:36:49")
@StaticMetamodel(Pedido.class)
public class Pedido_ { 

    public static volatile SingularAttribute<Pedido, Date> fecha;
    public static volatile CollectionAttribute<Pedido, Lineapedido> lineapedidoCollection;
    public static volatile SingularAttribute<Pedido, Double> total;
    public static volatile SingularAttribute<Pedido, Integer> estado;
    public static volatile SingularAttribute<Pedido, Tienda> tienda;
    public static volatile SingularAttribute<Pedido, String> informacion;
    public static volatile SingularAttribute<Pedido, Integer> idpedido;
    public static volatile SingularAttribute<Pedido, Direccion> direccion;
    public static volatile SingularAttribute<Pedido, Usuario> usuario;

}