package jpa.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import jpa.entity.Categoriasdetienda;
import jpa.entity.Producto;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-10-23T13:36:49")
@StaticMetamodel(Categoria.class)
public class Categoria_ { 

    public static volatile SingularAttribute<Categoria, Integer> idcategoria;
    public static volatile CollectionAttribute<Categoria, Producto> productoCollection;
    public static volatile CollectionAttribute<Categoria, Categoriasdetienda> categoriasdetiendaCollection;
    public static volatile SingularAttribute<Categoria, String> nombre;

}