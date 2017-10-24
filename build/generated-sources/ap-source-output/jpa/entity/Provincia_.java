package jpa.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import jpa.entity.Ciudad;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-23T13:36:49")
@StaticMetamodel(Provincia.class)
public class Provincia_ { 

    public static volatile CollectionAttribute<Provincia, Ciudad> ciudadCollection;
    public static volatile SingularAttribute<Provincia, Integer> idprovincia;
    public static volatile SingularAttribute<Provincia, String> nombre;

}