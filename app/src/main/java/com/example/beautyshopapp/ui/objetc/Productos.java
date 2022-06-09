package com.example.beautyshopapp.ui.objetc;

public class Productos {

    String codigo;
    String nombre;
    String cantidad;
    String precio;
    String proveedor;
    String imagen;

    public Productos() {}

    public Productos(String codigo, String nombre, String cantidad, String precio, String proveedor, String imagen) {

        this.codigo = codigo;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.proveedor = proveedor;
        this.imagen = imagen;
    }



    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCantidad() {
        return cantidad;
    }

    public String getPrecio() {
        return precio;
    }

    public String getProveedor() {
        return proveedor;
    }

    public String getImagen() {
        return imagen;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }


    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
