package com.HistorialClinico.Backend.controller;

import com.HistorialClinico.Backend.model.Rol;
import com.HistorialClinico.Backend.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/roles")
public class RolController {

    @Autowired
    private RolService rolService;

    // Endpoint para obtener la lista de roles
    @GetMapping
    public ResponseEntity<List<Rol>> listarRoles() {
        List<Rol> roles = rolService.findAllRoles();
        return ResponseEntity.ok(roles);  // Devolvemos directamente la lista de roles
    }

}

