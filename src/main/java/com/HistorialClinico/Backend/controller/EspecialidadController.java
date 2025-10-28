// src/main/java/com/HistorialClinico/Backend/controller/EspecialidadController.java
package com.HistorialClinico.Backend.controller;

import com.HistorialClinico.Backend.model.Especialidad;
import com.HistorialClinico.Backend.model.Horario;
import com.HistorialClinico.Backend.model.Usuario;
import com.HistorialClinico.Backend.service.EspecialidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/especialidades")
public class EspecialidadController {

    @Autowired
    private EspecialidadService especialidadService;

    // Obtener todas las especialidades
    @GetMapping
    public ResponseEntity<List<Especialidad>> obtenerTodasLasEspecialidades() {
        List<Especialidad> especialidades = especialidadService.getAllEspecialidades();
        return ResponseEntity.ok(especialidades);
    }

    // Crear una nueva especialidad
    @PostMapping
    public ResponseEntity<Especialidad> crearEspecialidad(@RequestBody Especialidad especialidad) {
        Especialidad nuevaEspecialidad = especialidadService.saveEspecialidad(especialidad);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaEspecialidad);
    }

    // Obtener una especialidad por ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obtenerEspecialidadPorId(@PathVariable Long id) {
        Optional<Especialidad> especialidad = especialidadService.getEspecialidadById(id);
        Map<String, Object> response = new HashMap<>();
        if (especialidad.isPresent()) {
            response.put("especialidad", especialidad.get());
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "Especialidad no encontrada");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Actualizar una especialidad por ID
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizarEspecialidad(@PathVariable Long id, @RequestBody Especialidad especialidadActualizada) {
        Optional<Especialidad> especialidad = especialidadService.updateEspecialidad(id, especialidadActualizada);
        Map<String, Object> response = new HashMap<>();
        if (especialidad.isPresent()) {
            response.put("especialidad", especialidad.get());
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "Especialidad no encontrada");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Eliminar una especialidad por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminarEspecialidad(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        if (especialidadService.deleteEspecialidad(id)) {
            response.put("message", "Especialidad eliminada exitosamente");
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "Especialidad no encontrada");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }


    // Obtener los médicos de una especialidad específica
    @GetMapping("/{id}/medicos")
    public ResponseEntity<List<Usuario>> obtenerMedicosPorEspecialidad(@PathVariable Long id) {
        List<Usuario> medicos = especialidadService.getMedicosPorEspecialidad(id);
        return ResponseEntity.ok(medicos);
    }

    // src/main/java/com/HistorialClinico/Backend/controller/EspecialidadController.java
    @PostMapping("/{especialidadId}/asignar-horario")
    public ResponseEntity<Map<String, Object>> asignarHorarioAEspecialidad(
            @PathVariable Long especialidadId, @RequestParam Long horarioId) {
        Map<String, Object> response = new HashMap<>();
        try {
            especialidadService.asignarHorarioAEspecialidad(especialidadId, horarioId);
            response.put("message", "Horario asignado correctamente a la especialidad");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.put("error", "Error al asignar el horario a la especialidad");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }



    // Agregar este método en el controlador
    @GetMapping("/{especialidadId}/horarios")
    public ResponseEntity<List<Horario>> obtenerHorariosPorEspecialidad(@PathVariable Long especialidadId) {
        Optional<Especialidad> especialidad = especialidadService.getEspecialidadById(especialidadId);
        if (especialidad.isPresent()) {
            List<Horario> horarios = new ArrayList<>(especialidad.get().getHorarios());
            return ResponseEntity.ok(horarios);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
