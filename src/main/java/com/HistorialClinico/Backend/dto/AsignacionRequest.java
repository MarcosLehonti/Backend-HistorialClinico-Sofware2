// src/main/java/com/HistorialClinico/Backend/dto/AsignacionRequest.java
package com.HistorialClinico.Backend.dto;

import java.util.List;

public class AsignacionRequest {
    private Long usuarioId;
    private Long especialidadId;
    private Long turnoId;
    private List<Long> diaIds;
    private String nombreUsuarioLogeado; // Campo para el nombre del usuario logueado


    // Getters y Setters
    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getEspecialidadId() {
        return especialidadId;
    }

    public void setEspecialidadId(Long especialidadId) {
        this.especialidadId = especialidadId;
    }

    public Long getTurnoId() {
        return turnoId;
    }

    public void setTurnoId(Long turnoId) {
        this.turnoId = turnoId;
    }

    public List<Long> getDiaIds() {
        return diaIds;
    }

    public void setDiaIds(List<Long> diaIds) {
        this.diaIds = diaIds;
    }

    public String getNombreUsuarioLogeado() {
        return nombreUsuarioLogeado;
    }

    public void setNombreUsuarioLogeado(String nombreUsuarioLogeado) {
        this.nombreUsuarioLogeado = nombreUsuarioLogeado;
    }
}
