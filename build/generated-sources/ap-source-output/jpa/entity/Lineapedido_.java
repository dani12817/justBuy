package jpa.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import jpa.entity.Pedido;
import jpa.entity.Producto;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-23T13:36:49")
@StaticMetamodel(Lineapedido.class)
public class Lineapedido_ { 

    public static volatile SingularAttribute<Lineapedido, Pedido> pedido;
    public static volatile SingularAttribute<Lineapedido, Integer> cantidad;
    public static volatile SingularAttribute<Lineapedido, Producto> producto;
    public static volatile SingularAttribute<Lineapedido, Integer> idlineaPedido;

}