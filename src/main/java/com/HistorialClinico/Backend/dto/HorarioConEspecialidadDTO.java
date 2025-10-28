package com.HistorialClinico.Backend.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class HorarioConEspecialidadDTO {
    private Long id;
    private DayOfWeek dia;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String especialidad;

    // Constructor
    public HorarioConEspecialidadDTO(Long id, DayOfWeek dia, LocalTime horaInicio, LocalTime horaFin, String especialidad) {
        this.id = id;
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.especialidad = especialidad;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public DayOfWeek getDia() {
        return dia;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    // Setters (opcionales, si necesitas modificarlos después de la creación del objeto)
}
