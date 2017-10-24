package jpa.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import jpa.entity.Usuario;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-23T13:36:49")
@StaticMetamodel(Pagopaypal.class)
public class Pagopaypal_ { 

    public static volatile SingularAttribute<Pagopaypal, String> mail;
    public static volatile SingularAttribute<Pagopaypal, Integer> idpagoPaypal;
    public static volatile SingularAttribute<Pagopaypal, Usuario> usuario;
    public static volatile SingularAttribute<Pagopaypal, String> titular;

}