package com.HistorialClinico.Backend.repository;

import com.HistorialClinico.Backend.model.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Long> {

    // Método personalizado para obtener los horarios de un médico, ya que la especialidad fue eliminada
    @Query("SELECT h FROM Horario h JOIN h.usuarios u WHERE u.id = :medicoId")
    List<Horario> findHorariosByMedicoId(@Param("medicoId") Long medicoId);

    List<Horario> findByTurnoId(Long turnoId);

}
