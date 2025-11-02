package com.HistorialClinico.Backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * DTO para envío de notificaciones por email al microservicio
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmailNotificationDTO {
    
    private String email;
    private String nombrePaciente;
    private String fecha;
    private String hora;
    private String nombreMedico;
    private String especialidad;
    private String nombreUsuario;
    private String diagnostico;
    private String tratamiento;
    
    // Constructores
    public EmailNotificationDTO() {}
    
    // Builder methods para facilitar la creación de instancias
    public static EmailNotificationDTO forAppointmentConfirmation(
            String email, String nombrePaciente, String fecha, String hora, 
            String nombreMedico, String especialidad, String nombreUsuario) {
        EmailNotificationDTO dto = new EmailNotificationDTO();
        dto.email = email;
        dto.nombrePaciente = nombrePaciente;
        dto.fecha = fecha;
        dto.hora = hora;
        dto.nombreMedico = nombreMedico;
        dto.especialidad = especialidad;
        dto.nombreUsuario = nombreUsuario;
        return dto;
    }
    
    public static EmailNotificationDTO forDiagnosisNotification(
            String email, String nombrePaciente, String nombreMedico, 
            String especialidad, String fecha, String diagnostico, String tratamiento) {
        EmailNotificationDTO dto = new EmailNotificationDTO();
        dto.email = email;
        dto.nombrePaciente = nombrePaciente;
        dto.nombreMedico = nombreMedico;
        dto.especialidad = especialidad;
        dto.fecha = fecha;
        dto.diagnostico = diagnostico;
        dto.tratamiento = tratamiento;
        return dto;
    }
    
    // Getters y Setters
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getNombrePaciente() {
        return nombrePaciente;
    }
    
    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
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
    
    public String getNombreMedico() {
        return nombreMedico;
    }
    
    public void setNombreMedico(String nombreMedico) {
        this.nombreMedico = nombreMedico;
    }
    
    public String getEspecialidad() {
        return especialidad;
    }
    
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
    
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    
    public String getDiagnostico() {
        return diagnostico;
    }
    
    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }
    
    public String getTratamiento() {
        return tratamiento;
    }
    
    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }
}
