package com.HistorialClinico.Backend.dto;

import jakarta.validation.constraints.NotNull;

public class TriajeDTO {

    @NotNull(message = "El ID del paciente es obligatorio")
    private Long pacienteId;

    @NotNull(message = "El ID de la enfermera es obligatorio")
    private Long enfermeraId;

    @NotNull(message = "La temperatura es obligatoria")
    private double temperatura;

    @NotNull(message = "La fecha es obligatoria")
    private String fecha;

    @NotNull(message = "La hora es obligatoria")
    private String hora;

    @NotNull(message = "La frecuencia cardíaca es obligatoria")
    private int frecuenciaCardiaca;

    @NotNull(message = "La frecuencia respiratoria es obligatoria")
    private int frecuenciaRespiratoria;

    @NotNull(message = "La saturación de oxígeno es obligatoria")
    private int saturacionOxigeno;

    @NotNull(message = "El peso es obligatorio")
    private double peso;

    @NotNull(message = "La estatura es obligatoria")
    private double estatura;

    private String alergias;
    private String enfermedadesCronicas;

    @NotNull(message = "El motivo de la consulta es obligatorio")
    private String motivoConsulta;

    // Getters y Setters

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public Long getEnfermeraId() {
        return enfermeraId;
    }

    public void setEnfermeraId(Long enfermeraId) {
        this.enfermeraId = enfermeraId;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
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

    public int getFrecuenciaCardiaca() {
        return frecuenciaCardiaca;
    }

    public void setFrecuenciaCardiaca(int frecuenciaCardiaca) {
        this.frecuenciaCardiaca = frecuenciaCardiaca;
    }

    public int getFrecuenciaRespiratoria() {
        return frecuenciaRespiratoria;
    }

    public void setFrecuenciaRespiratoria(int frecuenciaRespiratoria) {
        this.frecuenciaRespiratoria = frecuenciaRespiratoria;
    }

    public int getSaturacionOxigeno() {
        return saturacionOxigeno;
    }

    public void setSaturacionOxigeno(int saturacionOxigeno) {
        this.saturacionOxigeno = saturacionOxigeno;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getEstatura() {
        return estatura;
    }

    public void setEstatura(double estatura) {
        this.estatura = estatura;
    }

    public String getAlergias() {
        return alergias;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }

    public String getEnfermedadesCronicas() {
        return enfermedadesCronicas;
    }

    public void setEnfermedadesCronicas(String enfermedadesCronicas) {
        this.enfermedadesCronicas = enfermedadesCronicas;
    }

    public String getMotivoConsulta() {
        return motivoConsulta;
    }

    public void setMotivoConsulta(String motivoConsulta) {
        this.motivoConsulta = motivoConsulta;
    }
}
