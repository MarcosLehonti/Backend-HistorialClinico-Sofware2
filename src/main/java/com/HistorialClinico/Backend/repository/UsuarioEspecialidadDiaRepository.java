package com.HistorialClinico.Backend.repository;

import com.HistorialClinico.Backend.dto.UsuarioEspecialidadDiaDTO;
import com.HistorialClinico.Backend.model.UsuarioEspecialidadDia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioEspecialidadDiaRepository extends JpaRepository<UsuarioEspecialidadDia, Long> {
    boolean existsByUsuarioIdAndEspecialidadIdAndTurnoIdAndDiaId(Long usuarioId, Long especialidadId, Long turnoId, Long diaId);
    List<UsuarioEspecialidadDia> findByEspecialidadId(Long especialidadId);

    @Query("SELECT new com.HistorialClinico.Backend.dto.UsuarioEspecialidadDiaDTO(u.id, u.username, e.nombre, t.nombre, h.timeSlot, d.nombre, h.fecha, h.id, h.disponibilidad) " +
            "FROM UsuarioEspecialidadDia ued " +
            "JOIN ued.usuario u " +
            "JOIN ued.especialidad e " +
            "JOIN ued.turno t " +
            "JOIN Horario h ON t.id = h.turno.id " +
            "JOIN ued.dia d " +
            "WHERE e.id = :especialidadId AND h.disponibilidad = true")
    List<UsuarioEspecialidadDiaDTO> obtenerUsuariosYHorariosPorEspecialidad(@Param("especialidadId") Long especialidadId);
}
