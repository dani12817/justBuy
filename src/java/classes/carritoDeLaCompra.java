/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.text.DecimalFormat;
import jpa.entity.Producto;

/**
 *
 * @author dleal
 */
public class carritoDeLaCompra {
    private Producto prodcuto;
    private int cantidad;
    private double total;
    private static final DecimalFormat df = new DecimalFormat(".##");
    
    public carritoDeLaCompra(Producto producto, int cantidad){
        this.prodcuto = producto;
        this.cantidad = cantidad;
        this.total = producto.getPrecio();
    }

    public Producto getProdcuto() {
        return prodcuto;
    }

    public void setProdcuto(Producto prodcuto) {
        this.prodcuto = prodcuto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    public void subirCantidad(){
        cantidad++;
        total = Double.valueOf((df.format(cantidad * prodcuto.getPrecio())).replaceAll(",", "."));
    }
    
}
