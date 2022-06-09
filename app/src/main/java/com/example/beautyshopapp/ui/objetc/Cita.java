package com.example.beautyshopapp.ui.objetc;

import java.util.Date;

public class Cita {
    private String idCita;
    private String cliente;
    private String concepto;
    private String fecha;
    private String hora;
    private Boolean vigencia;
    public Cita(){

    }

    public Cita(String idCita, String cliente, String concepto, String fecha, String hora,Boolean vigencia) {
        this.idCita = idCita;
        this.cliente = cliente;
        this.concepto = concepto;
        this.fecha = fecha;
        this.hora = hora;
        this.vigencia = vigencia;
    }

    public String getIdCita() {
        return idCita;
    }

    public Boolean getVigencia() {
        return vigencia;
    }

    public void setVigencia(Boolean vigencia) {
        this.vigencia = vigencia;
    }

    public void setIdCita(String idCita) {
        this.idCita = idCita;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
