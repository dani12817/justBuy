package jpa.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import jpa.entity.Usuario;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-23T13:36:49")
@StaticMetamodel(Pagotarjeta.class)
public class Pagotarjeta_ { 

    public static volatile SingularAttribute<Pagotarjeta, Integer> idmetodoPago;
    public static volatile SingularAttribute<Pagotarjeta, String> caduca;
    public static volatile SingularAttribute<Pagotarjeta, Usuario> usuario;
    public static volatile SingularAttribute<Pagotarjeta, Integer> numeroTarjeta;
    public static volatile SingularAttribute<Pagotarjeta, String> titular;

}