package com.HistorialClinico.Backend.repository;

import com.HistorialClinico.Backend.model.Turno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Long> {
    // No es necesario agregar métodos adicionales, JpaRepository ofrece métodos básicos como findAll(), findById(), save(), etc.
}
