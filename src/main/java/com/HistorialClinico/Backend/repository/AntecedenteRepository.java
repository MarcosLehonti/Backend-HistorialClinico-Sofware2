package com.HistorialClinico.Backend.repository;

import com.HistorialClinico.Backend.model.Antecedente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AntecedenteRepository extends JpaRepository<Antecedente, Long> {
    List<Antecedente> findByUsuarioId(Long usuarioId);
    List<Antecedente> findByPacienteId(Long pacienteId); // Método para pacienteId

}
