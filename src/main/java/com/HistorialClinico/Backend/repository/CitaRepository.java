package com.HistorialClinico.Backend.repository;

import com.HistorialClinico.Backend.dto.CitaDTO;
import com.HistorialClinico.Backend.dto.CitaResponseDTO;
import com.HistorialClinico.Backend.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Long> {
    @Query("SELECT new com.HistorialClinico.Backend.dto.CitaResponseDTO(c.horario, c.nombreUsuarioLogeado, d.nombre, e.nombre, t.nombre) " +
            "FROM Cita c " +
            "JOIN c.dia d " +
            "JOIN c.especialidad e " +
            "JOIN c.turno t " +
            "WHERE c.usuario.id = :usuarioId")
    List<CitaResponseDTO> findCitasByUsuarioId(@Param("usuarioId") Long usuarioId);
    
    List<Cita> findByUsuarioId(Long usuarioId); // Método para encontrar citas por el ID del usuario (paciente)
    List<Cita> findByMedicoId(Long medicoId); // Método para encontrar citas por el ID del médico

    @Query("SELECT new com.HistorialClinico.Backend.dto.CitaResponseDTO(c.horario, c.nombreUsuarioLogeado, c.dia.nombre, c.especialidad.nombre, c.turno.nombre) " +
            "FROM Cita c WHERE c.medico.id = :medicoId")
    List<CitaResponseDTO> findCitasByMedicoId(@Param("medicoId") Long medicoId);


}
