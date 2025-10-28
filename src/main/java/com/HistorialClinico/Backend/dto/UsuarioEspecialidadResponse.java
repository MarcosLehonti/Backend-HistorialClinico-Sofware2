// UsuarioEspecialidadResponse.java
package com.HistorialClinico.Backend.dto;

import java.util.List;

public class UsuarioEspecialidadResponse {
    private String nombreUsuario;
    private String turno;
    private String dia;
    private List<String> horarios; // Lista de horarios específicos de atención

    // Getters y Setters
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public List<String> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<String> horarios) {
        this.horarios = horarios;
    }
}
