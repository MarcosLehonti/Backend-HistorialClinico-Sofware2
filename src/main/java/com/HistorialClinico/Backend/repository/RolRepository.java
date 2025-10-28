package com.HistorialClinico.Backend.repository;
// src/main/java/com/HistorialClinico/Backend/repository/RolRepository.java

import com.HistorialClinico.Backend.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombre(String nombre);


    boolean existsByNombre(String nombre);
}
