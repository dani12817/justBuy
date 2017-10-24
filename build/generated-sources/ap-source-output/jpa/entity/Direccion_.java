package jpa.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import jpa.entity.Codpostal;
import jpa.entity.Pedido;
import jpa.entity.Usuario;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-23T13:36:49")
@StaticMetamodel(Direccion.class)
public class Direccion_ { 

    public static volatile SingularAttribute<Direccion, Integer> iddireccion;
    public static volatile SingularAttribute<Direccion, String> direccionLinea2;
    public static volatile SingularAttribute<Direccion, String> direccionLinea1;
    public static volatile SingularAttribute<Direccion, Usuario> usuario;
    public static volatile CollectionAttribute<Direccion, Pedido> pedidoCollection;
    public static volatile SingularAttribute<Direccion, String> nombre;
    public static volatile SingularAttribute<Direccion, Codpostal> codPostal;

}