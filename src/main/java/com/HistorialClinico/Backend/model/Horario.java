//package com.HistorialClinico.Backend.model;
//
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import jakarta.persistence.*;
//
//import java.time.LocalTime;
//import java.util.HashSet;
//import java.util.Objects;
//import java.util.Set;
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//
//@Entity
//@Table(name = "horarios")
//public class Horario {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "time_slot", nullable = false)
//    private LocalTime timeSlot;
//
//    // Relación ManyToOne con Turno
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JsonBackReference
//
//    @JoinColumn(name = "turno_id", nullable = false)
//    private Turno turno;
//
//    // Relación Muchos a Muchos con Usuario (Médicos)
//    @ManyToMany(mappedBy = "horarios", fetch = FetchType.LAZY)
//    @JsonIgnore // Evita problemas de referencia cíclica
//    private Set<Usuario> usuarios = new HashSet<>();
//
//    // Constructor por defecto
//    public Horario() {
//    }
//
//    // Constructor con parámetros
//    public Horario(LocalTime timeSlot, Turno turno) {
//        this.timeSlot = timeSlot;
//        this.turno = turno;
//    }
//
//    // Getters y Setters
//
//    public Long getId() {
//        return id;
//    }
//
//    public LocalTime getTimeSlot() {
//        return timeSlot;
//    }
//
//    public void setTimeSlot(LocalTime timeSlot) {
//        this.timeSlot = timeSlot;
//    }
//
//    public Turno getTurno() {
//        return turno;
//    }
//
//    public void setTurno(Turno turno) {
//        this.turno = turno;
//    }
//
//    public Set<Usuario> getUsuarios() {
//        return usuarios;
//    }
//
//    public void setUsuarios(Set<Usuario> usuarios) {
//        this.usuarios = usuarios;
//    }
//
//    // Métodos de Utilidad para Gestionar la Relación
//
//    public void addUsuario(Usuario usuario) {
//        this.usuarios.add(usuario);
//        usuario.getHorarios().add(this);
//    }
//
//    public void removeUsuario(Usuario usuario) {
//        this.usuarios.remove(usuario);
//        usuario.getHorarios().remove(this);
//    }
//
//    // equals y hashCode basados en 'id'
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Horario horario = (Horario) o;
//        return Objects.equals(id, horario.id);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id);
//    }
//}


package com.HistorialClinico.Backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "horarios")
public class Horario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "time_slot", nullable = false)
    private LocalTime timeSlot;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "disponibilidad", nullable = false)
    private boolean disponibilidad;

    // Relación ManyToOne con Turno
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "turno_id", nullable = false)
    private Turno turno;

    // Relación Muchos a Muchos con Usuario (Médicos)
    @ManyToMany(mappedBy = "horarios", fetch = FetchType.LAZY)
    @JsonIgnore // Evita problemas de referencia cíclica
    private Set<Usuario> usuarios = new HashSet<>();

    // Constructor por defecto
    public Horario() {
    }

    // Constructor con parámetros
    public Horario(LocalTime timeSlot, LocalDate fecha, boolean disponibilidad, Turno turno) {
        this.timeSlot = timeSlot;
        this.fecha = fecha;
        this.disponibilidad = disponibilidad;
        this.turno = turno;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public LocalTime getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(LocalTime timeSlot) {
        this.timeSlot = timeSlot;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public Turno getTurno() {
        return turno;
    }

    public void setTurno(Turno turno) {
        this.turno = turno;
    }

    public Set<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    // Métodos de Utilidad para Gestionar la Relación

    public void addUsuario(Usuario usuario) {
        this.usuarios.add(usuario);
        usuario.getHorarios().add(this);
    }

    public void removeUsuario(Usuario usuario) {
        this.usuarios.remove(usuario);
        usuario.getHorarios().remove(this);
    }

    // equals y hashCode basados en 'id'

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Horario horario = (Horario) o;
        return Objects.equals(id, horario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
