package com.HistorialClinico.Backend.repository;

import com.HistorialClinico.Backend.model.Triaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TriajeRepository extends JpaRepository<Triaje, Long> {
    List<Triaje> findByPacienteId(Long pacienteId);


}
