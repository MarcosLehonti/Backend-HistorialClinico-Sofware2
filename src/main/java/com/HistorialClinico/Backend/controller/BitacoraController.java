package com.HistorialClinico.Backend.controller;

import com.HistorialClinico.Backend.model.Bitacora;
import com.HistorialClinico.Backend.repository.BitacoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bitacora")
public class BitacoraController {

    @Autowired
    private BitacoraRepository bitacoraRepository;

    @GetMapping
    public List<Bitacora> obtenerBitacora() {
        return bitacoraRepository.findAll();
    }
}

