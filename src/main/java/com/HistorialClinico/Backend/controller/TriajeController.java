package com.HistorialClinico.Backend.controller;

import com.HistorialClinico.Backend.dto.TriajeDTO;
import com.HistorialClinico.Backend.dto.TriajeResponseDTO;
import com.HistorialClinico.Backend.model.Triaje;
import com.HistorialClinico.Backend.service.BitacoraService;
import com.HistorialClinico.Backend.service.TriajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/triajes")
public class TriajeController {

    @Autowired
    private BitacoraService bitacoraService;

    private final TriajeService triajeService;

    @Autowired
    public TriajeController(TriajeService triajeService) {
        this.triajeService = triajeService;
    }

    @PostMapping("/crear")
    public ResponseEntity<Triaje> crearTriaje(@Valid @RequestBody TriajeDTO triajeDTO) {
        Triaje nuevoTriaje = triajeService.crearTriaje(triajeDTO);

        // Registrar en la bitácora usando el ID de la enfermera
        bitacoraService.registrar(
                "Enfermera con ID " + triajeDTO.getEnfermeraId(), // Usuario que realizó la acción
                "Creación de Triaje", // Acción
                "Triaje creado con ID " + nuevoTriaje.getId() + " para el paciente con ID " + triajeDTO.getPacienteId() // Detalles
        );

        return ResponseEntity.ok(nuevoTriaje);
    }



    @GetMapping("/todos")
    public ResponseEntity<List<TriajeResponseDTO>> obtenerTodosLosTriajes() {
        List<TriajeResponseDTO> triajes = triajeService.obtenerTodosLosTriajes();
        return ResponseEntity.ok(triajes);
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<TriajeResponseDTO>> obtenerTriajesPorPacienteId(@PathVariable Long pacienteId) {
        List<TriajeResponseDTO> triajes = triajeService.obtenerTriajesPorPacienteId(pacienteId);
        return ResponseEntity.ok(triajes);
    }




}
