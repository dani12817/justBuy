package jpa.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import jpa.entity.Ciudad;
import jpa.entity.Direccion;
import jpa.entity.Tienda;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-23T13:36:49")
@StaticMetamodel(Codpostal.class)
public class Codpostal_ { 

    public static volatile CollectionAttribute<Codpostal, Tienda> tiendaCollection;
    public static volatile SingularAttribute<Codpostal, Integer> idcodPostal;
    public static volatile SingularAttribute<Codpostal, String> numero;
    public static volatile SingularAttribute<Codpostal, Ciudad> ciudad;
    public static volatile CollectionAttribute<Codpostal, Direccion> direccionCollection;

}