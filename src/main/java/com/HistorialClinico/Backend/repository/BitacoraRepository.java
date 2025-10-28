package com.HistorialClinico.Backend.repository;

import com.HistorialClinico.Backend.model.Bitacora;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BitacoraRepository extends JpaRepository<Bitacora, Long> {
}

