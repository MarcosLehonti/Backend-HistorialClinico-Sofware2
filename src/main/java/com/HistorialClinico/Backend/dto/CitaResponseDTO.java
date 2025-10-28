package com.HistorialClinico.Backend.dto;

public class CitaResponseDTO {
    private String horario;
    private String nombreUsuarioLogeado;
    private String dia;
    private String especialidad;
    private String turno;

    // Constructor
    public CitaResponseDTO(String horario, String nombreUsuarioLogeado, String dia, String especialidad, String turno) {
        this.horario = horario;
        this.nombreUsuarioLogeado = nombreUsuarioLogeado;
        this.dia = dia;
        this.especialidad = especialidad;
        this.turno = turno;
    }

    // Getters y Setters
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

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
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
}
