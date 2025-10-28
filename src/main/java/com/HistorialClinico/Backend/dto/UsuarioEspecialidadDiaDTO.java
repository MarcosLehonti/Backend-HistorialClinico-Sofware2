package com.HistorialClinico.Backend.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class UsuarioEspecialidadDiaDTO {
    private Long usuarioId;
    private String usuario;
    private String especialidad;
    private String turno;
    private LocalTime horario;
    private String dia;
    private LocalDate fecha;         // Campo para la fecha
    private Long horarioId;          // Campo para el ID del horario
    private boolean disponibilidad;  // Campo para la disponibilidad

    // Constructor actualizado para incluir fecha, horarioId y disponibilidad
    public UsuarioEspecialidadDiaDTO(Long usuarioId, String usuario, String especialidad, String turno, LocalTime horario, String dia, LocalDate fecha, Long horarioId, boolean disponibilidad) {
        this.usuarioId = usuarioId;
        this.usuario = usuario;
        this.especialidad = especialidad;
        this.turno = turno;
        this.horario = horario;
        this.dia = dia;
        this.fecha = fecha;
        this.horarioId = horarioId;
        this.disponibilidad = disponibilidad;
    }

    // Getters y Setters
    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
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

    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }
}
