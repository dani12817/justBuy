package jpa.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import jpa.entity.Codpostal;
import jpa.entity.Provincia;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-23T13:36:49")
@StaticMetamodel(Ciudad.class)
public class Ciudad_ { 

    public static volatile CollectionAttribute<Ciudad, Codpostal> codpostalCollection;
    public static volatile SingularAttribute<Ciudad, Integer> idciudad;
    public static volatile SingularAttribute<Ciudad, Provincia> provincia;
    public static volatile SingularAttribute<Ciudad, String> nombre;

}