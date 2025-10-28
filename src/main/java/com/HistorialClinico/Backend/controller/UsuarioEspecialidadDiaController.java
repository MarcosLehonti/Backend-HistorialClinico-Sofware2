package com.HistorialClinico.Backend.controller;

import com.HistorialClinico.Backend.dto.AsignacionRequest;
import com.HistorialClinico.Backend.dto.UsuarioEspecialidadDiaDTO;
import com.HistorialClinico.Backend.dto.UsuarioEspecialidadResponse;
import com.HistorialClinico.Backend.model.*;
import com.HistorialClinico.Backend.repository.UsuarioEspecialidadDiaRepository;
import com.HistorialClinico.Backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/asignaciones")
public class UsuarioEspecialidadDiaController {

    @Autowired
    private BitacoraService bitacoraService;
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EspecialidadService especialidadService;

    @Autowired
    private UsuarioEspecialidadDiaService usuarioEspecialidadDiaService;
    @Autowired
    private TurnoService turnoService; // Añadido el servicio de turnos

    @Autowired
    private DiaService diaService; // Añadido el servicio de días



    private final UsuarioEspecialidadDiaRepository usuarioEspecialidadDiaRepository;

    public UsuarioEspecialidadDiaController(UsuarioEspecialidadDiaRepository usuarioEspecialidadDiaRepository) {
        this.usuarioEspecialidadDiaRepository = usuarioEspecialidadDiaRepository;
    }

    // Obtener todos los usuarios
    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> obtenerUsuarios() {
        List<Usuario> usuarios = usuarioService.findAllUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    // Obtener todos los turnos
    @GetMapping("/turnos")
    public ResponseEntity<List<Turno>> obtenerTurnos() {
        List<Turno> turnos = turnoService.obtenerTodosTurnos();
        return ResponseEntity.ok(turnos);
    }
    // Obtener todas las especialidades
    @GetMapping("/especialidades")
    public ResponseEntity<List<Especialidad>> obtenerEspecialidades() {
        List<Especialidad> especialidades = especialidadService.obtenerTodasEspecialidades();
        return ResponseEntity.ok(especialidades);
    }

    // Obtener todos los días de la base de datos
    @GetMapping("/dias")
    public ResponseEntity<List<Dia>> obtenerDias() {
        List<Dia> dias = diaService.obtenerTodosLosDias();
        return ResponseEntity.ok(dias);
    }
//    // Obtener todos los días (estáticos)
//    @GetMapping("/dias")
//    public ResponseEntity<List<String>> obtenerDias() {
//        List<String> dias = List.of("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo");
//        return ResponseEntity.ok(dias);
//    }

//    // Asignar especialidad y día a un usuario
//    @PostMapping("/asignar")
//    public ResponseEntity<UsuarioEspecialidadDia> asignarEspecialidadDia(@RequestBody UsuarioEspecialidadDia usuarioEspecialidadDia) {
//        UsuarioEspecialidadDia asignacion = usuarioEspecialidadDiaService.asignarEspecialidadDia(usuarioEspecialidadDia);
//        return ResponseEntity.ok(asignacion);
//    }



//    @PostMapping("/asignar")
//    public ResponseEntity<?> asignarEspecialidadDia(@RequestBody Map<String, Object> request) {
//        Long usuarioId = Long.parseLong(request.get("usuarioId").toString());
//        Long especialidadId = Long.parseLong(request.get("especialidadId").toString());
//        Long turnoId = Long.parseLong(request.get("turnoId").toString());
//        List<Integer> diaIds = (List<Integer>) request.get("diaIds");
//
//        // Iterar sobre cada `diaId` y crear una asignación para cada día
//        for (Integer diaId : diaIds) {
//            UsuarioEspecialidadDia asignacion = new UsuarioEspecialidadDia();
//            asignacion.setUsuarioId(usuarioId);
//            asignacion.setEspecialidadId(especialidadId);
//            asignacion.setTurnoId(turnoId);
//            asignacion.setDiaId(diaId.longValue());
//
//            usuarioEspecialidadDiaService.asignarEspecialidadDia(asignacion);
//        }
//
//        return ResponseEntity.ok("Asignaciones creadas exitosamente");
//    }


    @PostMapping("/asignar")
    public ResponseEntity<Map<String, String>> asignarEspecialidadDia(@RequestBody AsignacionRequest request) {
        Long usuarioId = request.getUsuarioId();
        Long especialidadId = request.getEspecialidadId();
        Long turnoId = request.getTurnoId();
        List<Long> diaIds = request.getDiaIds();

        // Iterar sobre cada `diaId` y crear una asignación para cada día
        for (Long diaId : diaIds) {
            UsuarioEspecialidadDia asignacion = new UsuarioEspecialidadDia();
            asignacion.setUsuarioId(usuarioId);
            asignacion.setEspecialidadId(especialidadId);
            asignacion.setTurnoId(turnoId);
            asignacion.setDiaId(diaId);

            usuarioEspecialidadDiaService.asignarEspecialidadDia(asignacion);
        }

        // Registro en la bitácora después de la asignación
        String usuarioLogeado = request.getNombreUsuarioLogeado(); // Asegúrate de tener el nombre del usuario logueado en el request
        bitacoraService.registrar(
                usuarioLogeado, // Usuario que realizó la asignación
                "Asignación de Especialidad y Día", // Acción
                "Especialidad con ID " + especialidadId + " asignada al usuario con ID " + usuarioId +
                        " en los días con IDs " + diaIds + " y turno con ID " + turnoId // Detalles
        );

        // Devolver un objeto JSON en lugar de texto plano
        Map<String, String> response = new HashMap<>();
        response.put("message", "Asignaciones creadas exitosamente");
        return ResponseEntity.ok(response);
    }



//
//    // Método para obtener usuarios, turnos, días y horarios según una especialidad
//    @GetMapping("/usuarios-especialidad/{especialidadId}")
//    public ResponseEntity<List<UsuarioEspecialidadResponse>> obtenerUsuariosPorEspecialidad(@PathVariable Long especialidadId) {
//        // Llama al servicio para obtener las asignaciones basadas en la especialidad
//        List<UsuarioEspecialidadDia> asignaciones = usuarioEspecialidadDiaService.obtenerAsignacionesPorEspecialidad(especialidadId);
//
//        // Transforma las asignaciones en respuestas con nombres de usuario, turno, día y horarios correspondientes
//        List<UsuarioEspecialidadResponse> respuesta = asignaciones.stream().map(asignacion -> {
//            UsuarioEspecialidadResponse usuarioEspecialidad = new UsuarioEspecialidadResponse();
//            usuarioEspecialidad.setNombreUsuario(asignacion.getUsuario().getUsername());
//            usuarioEspecialidad.setTurno(asignacion.getTurno().getNombre());
//            usuarioEspecialidad.setDia(asignacion.getDia().getNombre());
//
//            // Obtiene los horarios asociados al turno
//            List<Horario> horarios = usuarioEspecialidadDiaService.obtenerHorariosPorTurno(asignacion.getTurnoId());
//            List<String> horariosStrings = horarios.stream()
//                    .map(horario -> horario.getTimeSlot().toString())
//                    .collect(Collectors.toList());
//
//            usuarioEspecialidad.setHorarios(horariosStrings);
//            return usuarioEspecialidad;
//        }).collect(Collectors.toList());
//
//        return ResponseEntity.ok(respuesta);
//    }



    @GetMapping("/usuarios-por-especialidad/{especialidadId}")
    public List<UsuarioEspecialidadDiaDTO> obtenerUsuariosPorEspecialidad(@PathVariable Long especialidadId) {
        return usuarioEspecialidadDiaRepository.obtenerUsuariosYHorariosPorEspecialidad(especialidadId);
    }


}
