package com.HistorialClinico.Backend.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class CitaDTO {

    @NotNull(message = "El usuario ID es obligatorio")
    private Long usuarioId;

    @NotNull(message = "El médico ID es obligatorio")
    private Long medicoId; // Campo para el ID del médico

    @NotNull(message = "La especialidad ID es obligatoria")
    private Long especialidadId;

    @NotNull(message = "El turno ID es obligatorio")
    private Long turnoId;

    @NotNull(message = "El día ID es obligatorio")
    private Long diaId;

    @NotNull(message = "El horario es obligatorio")
    private String horario;

    @NotNull(message = "El nombre del usuario logueado es obligatorio")
    private String nombreUsuarioLogeado;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha; // Campo para la fecha

    @NotNull(message = "El horario ID es obligatorio")
    private Long horarioId; // Nuevo campo para el ID del horario

    // Getters y Setters
    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getMedicoId() {
        return medicoId;
    }

    public void setMedicoId(Long medicoId) {
        this.medicoId = medicoId;
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

    public Long getDiaId() {
        return diaId;
    }

    public void setDiaId(Long diaId) {
        this.diaId = diaId;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getNombreUsuarioLogeado() {
        return nombreUsuarioLogeado;
    }

    public void setNombreUsuarioLogeado(String nombreUsuarioLogeado) {
        this.nombreUsuarioLogeado = nombreUsuarioLogeado;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Long getHorarioId() {
        return horarioId;
    }

    public void setHorarioId(Long horarioId) {
        this.horarioId = horarioId;
    }
}
