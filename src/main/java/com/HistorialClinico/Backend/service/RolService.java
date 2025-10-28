package com.HistorialClinico.Backend.service;

// src/main/java/com/HistorialClinico/Backend/service/RolService.java

import com.HistorialClinico.Backend.model.Rol;
import com.HistorialClinico.Backend.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    public Optional<Rol> findByNombre(String nombre) {
        return rolRepository.findByNombre(nombre);
    }

    public Rol saveRol(Rol rol) {
        return rolRepository.save(rol);
    }

    public List<Rol> findAllRoles() {
        return rolRepository.findAll();
    }

    public boolean existsByNombre(String nombre) {
        return rolRepository.existsByNombre(nombre);
    }
}
