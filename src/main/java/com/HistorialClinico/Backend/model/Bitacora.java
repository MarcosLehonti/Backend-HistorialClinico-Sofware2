package com.HistorialClinico.Backend.model;

import jakarta.persistence.*;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class Bitacora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String usuario; // Nombre del usuario o ID
    private String accion;  // Descripción de la acción realizada
    private String detalles; // Detalles adicionales de la acción

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha; // Fecha y hora del evento

    // Constructor vacío
    public Bitacora() {}

    // Constructor con parámetros
    public Bitacora(String usuario, String accion, String detalles, Date fecha) {
        this.usuario = usuario;
        this.accion = accion;
        this.detalles = detalles;
        this.fecha = fecha;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Bitacora{" +
                "id=" + id +
                ", usuario='" + usuario + '\'' +
                ", accion='" + accion + '\'' +
                ", detalles='" + detalles + '\'' +
                ", fecha=" + fecha +
                '}';
    }
}
