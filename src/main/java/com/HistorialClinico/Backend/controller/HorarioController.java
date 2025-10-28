package com.HistorialClinico.Backend.controller;

import com.HistorialClinico.Backend.model.Horario;
import com.HistorialClinico.Backend.model.Usuario;
import com.HistorialClinico.Backend.service.HorarioService;
import com.HistorialClinico.Backend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/horarios")
public class HorarioController {

    @Autowired
    private HorarioService horarioService;

    @Autowired
    private UsuarioService usuarioService;

    // Crear un nuevo horario
    @PostMapping
    public ResponseEntity<?> crearHorario(@RequestBody Horario horario) {
        try {
            Horario nuevoHorario = horarioService.crearHorario(horario);
            return new ResponseEntity<>(nuevoHorario, HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error al crear el horario.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener todos los horarios
    @GetMapping
    public ResponseEntity<List<Horario>> obtenerHorarios() {
        List<Horario> horarios = horarioService.obtenerHorarios();
        return ResponseEntity.ok(horarios);
    }

    // Obtener un horario por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerHorarioPorId(@PathVariable Long id) {
        try {
            Horario horario = horarioService.obtenerHorarioPorId(id);
            return ResponseEntity.ok(horario);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

//    // Actualizar un horario existente
//    @PutMapping("/{id}")
//    public ResponseEntity<?> actualizarHorario(@PathVariable Long id, @RequestBody Horario horarioDetalles) {
//        try {
//            Horario horarioActualizado = horarioService.actualizarHorario(id, horarioDetalles);
//            return ResponseEntity.ok(horarioActualizado);
//        } catch (IllegalArgumentException ex) {
//            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
//        } catch (RuntimeException ex) {
//            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
//        }
//    }

//    // Eliminar un horario
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> eliminarHorario(@PathVariable Long id) {
//        try {
//            horarioService.eliminarHorario(id);
//            return ResponseEntity.noContent().build();
//        } catch (RuntimeException ex) {
//            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
//        }
//    }

    // Asignar un horario a un médico
    @PostMapping("/asignar")
    public ResponseEntity<Map<String, String>> asignarHorarioAMedico(@RequestParam Long medicoId, @RequestParam Long horarioId) {
        Map<String, String> response = new HashMap<>();

        try {
            horarioService.asignarHorarioAMedico(medicoId, horarioId);
            response.put("message", "Horario asignado correctamente al médico.");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException ex) {
            response.put("error", ex.getMessage());
            return ResponseEntity.badRequest().body(response);
        } catch (RuntimeException ex) {
            response.put("error", ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Obtener horarios por médico
    @GetMapping("/medico/{medicoId}")
    public ResponseEntity<?> obtenerHorariosPorMedico(@PathVariable Long medicoId) {
        try {
            Set<Horario> horarios = horarioService.obtenerHorariosPorMedico(medicoId);
            return ResponseEntity.ok(horarios);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Obtener detalles de los médicos
    @GetMapping("/medicos/detalles")
    public ResponseEntity<List<Usuario>> obtenerMedicosConDetalles() {
        List<Usuario> medicos = usuarioService.obtenerMedicos();
        return ResponseEntity.ok(medicos);
    }
}
