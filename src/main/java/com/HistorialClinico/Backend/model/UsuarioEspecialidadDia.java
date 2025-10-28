package com.HistorialClinico.Backend.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "usuario_especialidad_dia")
public class UsuarioEspecialidadDia implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con la entidad Usuario
    @ManyToOne
    @JoinColumn(name = "usuario_id", insertable = false, updatable = false)
    private Usuario usuario;

    // Relación con la entidad Especialidad
    @ManyToOne
    @JoinColumn(name = "especialidad_id", insertable = false, updatable = false)
    private Especialidad especialidad;

    // Relación con la entidad Turno
    @ManyToOne
    @JoinColumn(name = "turno_id", insertable = false, updatable = false)
    private Turno turno;

    // Relación con la entidad Dia
    @ManyToOne
    @JoinColumn(name = "dia_id", insertable = false, updatable = false)
    private Dia dia;

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    public Dia getDia() {
        return dia;
    }

    public void setDia(Dia dia) {
        this.dia = dia;
    }

    // Setters para los IDs en caso de que necesites asignarlos manualmente

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Column(name = "especialidad_id", nullable = false)
    private Long especialidadId;

    public Long getEspecialidadId() {
        return especialidadId;
    }

    public void setEspecialidadId(Long especialidadId) {
        this.especialidadId = especialidadId;
    }

    @Column(name = "turno_id", nullable = false)
    private Long turnoId;

    public Long getTurnoId() {
        return turnoId;
    }

    public void setTurnoId(Long turnoId) {
        this.turnoId = turnoId;
    }

    @Column(name = "dia_id", nullable = false)
    private Long diaId;

    public Long getDiaId() {
        return diaId;
    }

    public void setDiaId(Long diaId) {
        this.diaId = diaId;
    }
}
