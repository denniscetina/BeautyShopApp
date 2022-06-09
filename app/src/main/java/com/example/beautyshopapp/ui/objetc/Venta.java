package com.example.beautyshopapp.ui.objetc;

public class Venta {
    private String idVenta;
    private String clienteVenta;
    private String fechaVenta;
    private String valorVenta;
    private String conceptoVenta;
    private String metodoPago;

    public Venta(String idVenta, String clienteVenta, String fechaVenta, String valorVenta, String conceptoVenta, String metodoPago) {
        this.idVenta = idVenta;
        this.clienteVenta = clienteVenta;
        this.fechaVenta = fechaVenta;
        this.valorVenta = valorVenta;
        this.conceptoVenta = conceptoVenta;
        this.metodoPago = metodoPago;
    }
    public Venta(){

    }

    public String getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(String idVenta) {
        this.idVenta = idVenta;
    }

    public String getClienteVenta() {
        return clienteVenta;
    }

    public void setClienteVenta(String clienteventa) {
        this.clienteVenta = clienteventa;
    }

    public String getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public String getValorVenta() {
        return valorVenta;
    }

    public void setValorVenta(String valorVenta) {
        this.valorVenta = valorVenta;
    }

    public String getConceptoVenta() {
        return conceptoVenta;
    }

    public void setConceptoVenta(String conceptoVenta) {
        this.conceptoVenta = conceptoVenta;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }
}
