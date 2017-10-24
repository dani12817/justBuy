package jpa.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import jpa.entity.Producto;
import jpa.entity.Usuario;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-23T13:36:49")
@StaticMetamodel(Mensaje.class)
public class Mensaje_ { 

    public static volatile SingularAttribute<Mensaje, Date> fecha;
    public static volatile SingularAttribute<Mensaje, String> asunto;
    public static volatile SingularAttribute<Mensaje, Usuario> remitente;
    public static volatile SingularAttribute<Mensaje, String> mensaje;
    public static volatile SingularAttribute<Mensaje, Producto> producto;
    public static volatile SingularAttribute<Mensaje, Integer> idmensaje;
    public static volatile SingularAttribute<Mensaje, Usuario> destinatario;

}