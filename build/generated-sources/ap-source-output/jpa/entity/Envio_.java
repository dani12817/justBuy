package jpa.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import jpa.entity.Tienda;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-23T13:36:49")
@StaticMetamodel(Envio.class)
public class Envio_ { 

    public static volatile SingularAttribute<Envio, Double> precioEnvio;
    public static volatile SingularAttribute<Envio, Tienda> tienda;
    public static volatile SingularAttribute<Envio, Integer> idenvio;
    public static volatile SingularAttribute<Envio, String> nombre;

}