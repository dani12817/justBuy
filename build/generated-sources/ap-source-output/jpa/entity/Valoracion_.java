package jpa.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import jpa.entity.Tienda;
import jpa.entity.Usuario;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-23T13:36:49")
@StaticMetamodel(Valoracion.class)
public class Valoracion_ { 

    public static volatile SingularAttribute<Valoracion, String> texto;
    public static volatile SingularAttribute<Valoracion, Date> fecha;
    public static volatile SingularAttribute<Valoracion, Tienda> tienda;
    public static volatile SingularAttribute<Valoracion, Integer> puntuacion;
    public static volatile SingularAttribute<Valoracion, Integer> idvaloracion;
    public static volatile SingularAttribute<Valoracion, Usuario> usuario;

}