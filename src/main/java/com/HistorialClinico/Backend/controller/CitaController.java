package com.HistorialClinico.Backend.controller;

import com.HistorialClinico.Backend.dto.CitaDTO;
import com.HistorialClinico.Backend.dto.CitaResponseDTO;
import com.HistorialClinico.Backend.model.Cita;
import com.HistorialClinico.Backend.service.BitacoraService;
import com.HistorialClinico.Backend.service.CitaService;
import com.HistorialClinico.Backend.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    @Autowired
    private BitacoraService bitacoraService;

    private static final Logger log = LoggerFactory.getLogger(CitaController.class);

    private final CitaService citaService;
    private final EmailService emailService;

    @Autowired
    public CitaController(CitaService citaService, EmailService emailService) {
        this.citaService = citaService;
        this.emailService = emailService;
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearCita(@Valid @RequestBody CitaDTO citaDTO) {
        // Validaciones
        if (citaDTO.getUsuarioId() == null) {
            log.warn("Usuario ID es nulo");
            return ResponseEntity.badRequest().body("Error: Usuario ID es requerido");
        }
        if (citaDTO.getMedicoId() == null) {
            log.warn("Médico ID es nulo");
            return ResponseEntity.badRequest().body("Error: Médico ID es requerido");
        }
        if (citaDTO.getEspecialidadId() == null) {
            log.warn("Especialidad ID es nulo");
            return ResponseEntity.badRequest().body("Error: Especialidad ID es requerido");
        }
        if (citaDTO.getTurnoId() == null) {
            log.warn("Turno ID es nulo");
            return ResponseEntity.badRequest().body("Error: Turno ID es requerido");
        }
        if (citaDTO.getDiaId() == null) {
            log.warn("Día ID es nulo");
            return ResponseEntity.badRequest().body("Error: Día ID es requerido");
        }
        if (citaDTO.getHorario() == null) {
            log.warn("Horario es nulo");
            return ResponseEntity.badRequest().body("Error: Horario es requerido");
        }
        if (citaDTO.getNombreUsuarioLogeado() == null) {
            log.warn("Nombre del usuario logueado es nulo");
            return ResponseEntity.badRequest().body("Error: Nombre del usuario logueado es requerido");
        }

        // Crear la cita
        Cita nuevaCita = citaService.crearCita(citaDTO);

        // Enviar correo solo si la cita se creó con éxito
        if (nuevaCita != null) {
            try {
                String emailUsuario = nuevaCita.getUsuario().getEmail();
                emailService.enviarRecordatorioCita(emailUsuario, nuevaCita); // Envía el correo
                log.info("Correo de recordatorio enviado a " + emailUsuario);
            } catch (Exception e) {
                log.error("Error al enviar el correo de recordatorio: " + e.getMessage());
                // Puedes decidir si lanzar una advertencia o simplemente registrar el error sin afectar la creación
            }


            //registro de bitacora
            bitacoraService.registrar(
                    citaDTO.getNombreUsuarioLogeado(), // Usuario que creó la cita
                    "Creación de cita", // Acción
                    "Cita creada para el usuario con ID " + citaDTO.getUsuarioId() +
                            ", médico con ID " + citaDTO.getMedicoId() +
                            " y especialidad con ID " + citaDTO.getEspecialidadId() // Detalles
            );

        }

        return ResponseEntity.ok(nuevaCita);
    }



    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<CitaResponseDTO>> obtenerCitasPorUsuarioId(@PathVariable Long usuarioId) {
        List<CitaResponseDTO> citas = citaService.obtenerCitasPorUsuarioId(usuarioId);
        return ResponseEntity.ok(citas);
    }


    // CitaController.java
    @GetMapping("/citas-por-medico/{medicoId}")
    public ResponseEntity<List<CitaDTO>> obtenerCitasPorMedico(@PathVariable Long medicoId) {
        List<Cita> citas = citaService.obtenerCitasPorMedico(medicoId);
        List<CitaDTO> citasDTO = citas.stream().map(cita -> {
            CitaDTO dto = new CitaDTO();
            dto.setHorario(cita.getHorario());
            dto.setNombreUsuarioLogeado(cita.getNombreUsuarioLogeado());
            // map other fields as needed
            return dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(citasDTO);
    }


    @GetMapping("/medico/{medicoId}")
    public ResponseEntity<List<CitaResponseDTO>> obtenerCitasPorMedicoId(@PathVariable Long medicoId) {
        List<CitaResponseDTO> citas = citaService.obtenerCitasPorMedicoId(medicoId);
        return ResponseEntity.ok(citas);
    }

}
