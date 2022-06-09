package com.example.beautyshopapp.ui.objetc;

public class Gasto {
    private String idGasto;
    private String fechaGasto;
    private String categoriagasto;
    private String valorGasto;
    private String conceptoGasto;
    private String metodoPagoGasto;
    public Gasto(){}

    public Gasto(String idGasto, String fechaGasto, String categoriagasto, String valorGasto, String conceptoGasto, String metodoPagoGasto) {
        this.idGasto = idGasto;
        this.fechaGasto = fechaGasto;
        this.categoriagasto = categoriagasto;
        this.valorGasto = valorGasto;
        this.conceptoGasto = conceptoGasto;
        this.metodoPagoGasto = metodoPagoGasto;
    }

    public String getIdGasto() {
        return idGasto;
    }

    public void setIdGasto(String idGasto) {
        this.idGasto = idGasto;
    }

    public String getFechaGasto() {
        return fechaGasto;
    }

    public void setFechaGasto(String fechaGasto) {
        this.fechaGasto = fechaGasto;
    }

    public String getCategoriagasto() {
        return categoriagasto;
    }

    public void setCategoriagasto(String categoriagasto) {
        this.categoriagasto = categoriagasto;
    }

    public String getValorGasto() {
        return valorGasto;
    }

    public void setValorGasto(String valorGasto) {
        this.valorGasto = valorGasto;
    }

    public String getConceptoGasto() {
        return conceptoGasto;
    }

    public void setConceptoGasto(String conceptoGasto) {
        this.conceptoGasto = conceptoGasto;
    }

    public String getMetodoPagoGasto() {
        return metodoPagoGasto;
    }

    public void setMetodoPagoGasto(String metodoPagoGasto) {
        this.metodoPagoGasto = metodoPagoGasto;
    }
}
