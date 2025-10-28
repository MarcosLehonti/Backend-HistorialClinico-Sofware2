package com.HistorialClinico.Backend.controller;

// src/main/java/com/HistorialClinico/Backend/controller/DiagnosticoController.java

import com.HistorialClinico.Backend.dto.DiagnosticoDTO;
import com.HistorialClinico.Backend.dto.DiagnosticoResponseDTO;
import com.HistorialClinico.Backend.model.Diagnostico;
import com.HistorialClinico.Backend.service.BitacoraService;
import com.HistorialClinico.Backend.service.DiagnosticoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/diagnosticos")
public class DiagnosticoController {
    @Autowired
    private BitacoraService bitacoraService;

    private final DiagnosticoService diagnosticoService;

    @Autowired
    public DiagnosticoController(DiagnosticoService diagnosticoService) {
        this.diagnosticoService = diagnosticoService;
    }

    @PostMapping("/crear")
    public ResponseEntity<Diagnostico> crearDiagnostico(@RequestBody DiagnosticoDTO diagnosticoDTO) {
        Diagnostico nuevoDiagnostico = diagnosticoService.crearDiagnostico(diagnosticoDTO);
        // Registrar en la bitácora
        bitacoraService.registrar(
                "Médico con ID " + diagnosticoDTO.getMedicoId(), // Usuario que realizó la acción
                "Creación de Diagnóstico", // Acción
                "Diagnóstico creado con ID " + nuevoDiagnostico.getId() + " para el paciente con ID " + diagnosticoDTO.getPacienteId() // Detalles
        );
        return ResponseEntity.ok(nuevoDiagnostico);
    }



    // Endpoint para obtener diagnósticos por medico_id
    @GetMapping("/medico/{medicoId}")
    public ResponseEntity<List<DiagnosticoResponseDTO>> obtenerDiagnosticosPorMedicoId(@PathVariable Long medicoId) {
        List<DiagnosticoResponseDTO> diagnosticos = diagnosticoService.obtenerDiagnosticosPorMedicoId(medicoId);
        return ResponseEntity.ok(diagnosticos);
    }

    // Endpoint para obtener diagnósticos por paciente_id
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<DiagnosticoResponseDTO>> obtenerDiagnosticosPorPacienteId(@PathVariable Long pacienteId) {
        List<DiagnosticoResponseDTO> diagnosticos = diagnosticoService.obtenerDiagnosticosPorPacienteId(pacienteId);
        return ResponseEntity.ok(diagnosticos);
    }
}
