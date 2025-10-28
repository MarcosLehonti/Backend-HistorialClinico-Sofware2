package com.HistorialClinico.Backend.repository;

// src/main/java/com/HistorialClinico/Backend/repository/DiagnosticoRepository.java

import com.HistorialClinico.Backend.model.Diagnostico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiagnosticoRepository extends JpaRepository<Diagnostico, Long> {
    // Método para obtener diagnósticos por medico_id
    List<Diagnostico> findByMedicoId(Long medicoId);

    // Método para obtener diagnósticos por paciente_id
    List<Diagnostico> findByPacienteId(Long pacienteId);
}

