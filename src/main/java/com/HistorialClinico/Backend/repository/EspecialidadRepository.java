// src/main/java/com/HistorialClinico/Backend/repository/EspecialidadRepository.java
package com.HistorialClinico.Backend.repository;

import com.HistorialClinico.Backend.model.Especialidad;
import com.HistorialClinico.Backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EspecialidadRepository extends JpaRepository<Especialidad, Long> {
    boolean existsByNombre(String nombre);
    // Consulta para obtener los médicos asociados a una especialidad
    @Query("SELECT u FROM Usuario u JOIN u.roles r JOIN u.especialidades e WHERE e.id = :especialidadId AND r.nombre = :role")
    List<Usuario> findMedicosByEspecialidadId(@Param("especialidadId") Long especialidadId, @Param("role") String role);

}
