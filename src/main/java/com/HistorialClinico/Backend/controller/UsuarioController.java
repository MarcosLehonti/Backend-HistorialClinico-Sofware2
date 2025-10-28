package com.HistorialClinico.Backend.controller;

import com.HistorialClinico.Backend.model.Especialidad;
import com.HistorialClinico.Backend.model.Horario;
import com.HistorialClinico.Backend.model.LoginRequest;
import com.HistorialClinico.Backend.model.Usuario;
import com.HistorialClinico.Backend.service.BitacoraService;
import com.HistorialClinico.Backend.service.JwtService;
import com.HistorialClinico.Backend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.HistorialClinico.Backend.model.Rol;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private BitacoraService bitacoraService;

    @Autowired
    private JwtService jwtService;

    // Crear un nuevo usuario
    @PostMapping(value = "/registro", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> registro(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.saveUsuario(usuario);
        // Registrar en la bitácora
        bitacoraService.registrar(
                "Administrador del sistema", // Usuario que realizó la acción
                "Registro de Nuevo Usuario", // Acción
                "Usuario creado con ID " + nuevoUsuario.getId() + " y nombre de usuario " + nuevoUsuario.getUsername() // Detalles
        );
        Map<String, Object> response = new HashMap<>();
        response.put("usuario", nuevoUsuario);
        return ResponseEntity.ok(response);
    }

    // Obtener usuario por nombre de usuario
    @GetMapping("/{username}")
    public ResponseEntity<Map<String, Object>> obtenerUsuario(@PathVariable String username) {
        Optional<Usuario> usuario = usuarioService.findByUsername(username);
        Map<String, Object> response = new HashMap<>();
        if (usuario.isPresent()) {
            response.put("usuario", usuario.get());
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "Usuario no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Listar todos los usuarios
    @GetMapping
    public ResponseEntity<Map<String, Object>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.findAllUsuarios();
        Map<String, Object> response = new HashMap<>();
        response.put("usuarios", usuarios);
        return ResponseEntity.ok(response);
    }

    // Actualizar un usuario por ID
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        Optional<Usuario> usuario = usuarioService.updateUsuario(id, usuarioActualizado);
        Map<String, Object> response = new HashMap<>();
        if (usuario.isPresent()) {
            response.put("usuario", usuario.get());
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "Usuario no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Eliminar un usuario por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> eliminarUsuario(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        if (usuarioService.deleteUsuario(id)) {
            response.put("message", "Usuario eliminado exitosamente");
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "Usuario no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Login
//    @PostMapping("/login")
//    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest loginRequest) {
//        Optional<Usuario> usuario = usuarioService.findByUsername(loginRequest.getUsername());
//        Map<String, Object> response = new HashMap<>();
//
//        if (usuario.isPresent() && usuarioService.checkPassword(loginRequest.getPassword(), usuario.get().getPassword())) {
//            String token = jwtService.generateToken(usuario.get());
//            response.put("token", token);
//            return ResponseEntity.ok(response);
//        } else {
//            response.put("error", "Credenciales incorrectas");
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
//        }
//    }
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest loginRequest) {
        Optional<Usuario> usuario = usuarioService.findByUsername(loginRequest.getUsername());
        Map<String, Object> response = new HashMap<>();

        if (usuario.isPresent() && usuarioService.checkPassword(loginRequest.getPassword(), usuario.get().getPassword())) {
            String token = jwtService.generateToken(usuario.get());

            // Obtener el primer rol del usuario (si solo hay un rol por usuario)
            String rol = usuario.get().getRoles().stream().findFirst().map(Rol::getNombre).orElse("ROLE_USER");

            response.put("token", token);
            response.put("usuarioId", usuario.get().getId()); // ID del usuario
            response.put("rol", rol); // Agrega el rol del usuario

            //registro de bitacora
            bitacoraService.registrar(
                   usuario.get().getUsername(),// usuario
                    "Inicio de Sesion",//accion
                    "Inicio de Sesion Exitoso para el usuario de rol "+ rol//detalles
            );

            return ResponseEntity.ok(response);
        } else {
            response.put("error", "Credenciales incorrectas");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }





    // Obtener datos del usuario logueado
    @GetMapping("/perfil")
    public ResponseEntity<Map<String, Object>> obtenerPerfil(@RequestHeader("Authorization") String token) {
        String username = jwtService.extractUsername(token.substring(7)); // Quitar "Bearer " del token
        Optional<Usuario> usuario = usuarioService.findByUsername(username);
        Map<String, Object> response = new HashMap<>();
        if (usuario.isPresent()) {
            response.put("usuario", usuario.get());
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "Usuario no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Actualizar perfil del usuario logueado
    @PutMapping("/perfil")
    public ResponseEntity<Map<String, Object>> actualizarPerfil(
            @RequestHeader("Authorization") String token,
            @RequestBody Usuario usuarioActualizado) {

        String username = jwtService.extractUsername(token.substring(7));
        Optional<Usuario> usuario = usuarioService.findByUsername(username);

        Map<String, Object> response = new HashMap<>();
        if (usuario.isPresent()) {
            Usuario usuarioExistente = usuario.get();
            usuarioExistente.setUsername(usuarioActualizado.getUsername());
            usuarioExistente.setEmail(usuarioActualizado.getEmail());
            usuarioExistente = usuarioService.saveUsuario(usuarioExistente);

            response.put("usuario", usuarioExistente);
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "Usuario no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    @GetMapping("/roles")
    public ResponseEntity<?> obtenerRoles() {
        return ResponseEntity.ok(usuarioService.obtenerTodosRoles());
    }

    @PostMapping("/{usuarioId}/roles")
    public ResponseEntity<?> asignarRol(@PathVariable Long usuarioId, @RequestBody Map<String, String> rol) {
        String nombreRol = rol.get("rol");
        boolean asignado = usuarioService.asignarRol(usuarioId, nombreRol);

        if (asignado) {
            // Cambiar esto para devolver un objeto JSON
            return ResponseEntity.ok(Map.of("message", "Rol asignado correctamente"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "No se pudo asignar el rol"));
        }
    }


    @GetMapping("/medicos")
    public ResponseEntity<List<Usuario>> obtenerMedicos() {
        List<Usuario> medicos = usuarioService.obtenerMedicos();
        return ResponseEntity.ok(medicos);
    }

//    @PostMapping("/{usuarioId}/especialidades")
//    public ResponseEntity<?> asignarEspecialidad(
//            @PathVariable Long usuarioId,
//            @RequestBody Map<String, Long> especialidadData) {
//
//        Long especialidadId = especialidadData.get("especialidadId");
//        boolean asignado = usuarioService.asignarEspecialidad(usuarioId, especialidadId);
//
//        if (asignado) {
//            return ResponseEntity.ok(Map.of("message", "Especialidad asignada correctamente"));
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(Map.of("error", "No se pudo asignar la especialidad"));
//        }
//    }


    @PostMapping("/{usuarioId}/especialidades")
    public ResponseEntity<?> asignarEspecialidades(
            @PathVariable Long usuarioId,
            @RequestBody Map<String, List<Long>> especialidadData) {

        List<Long> especialidadIds = especialidadData.get("especialidadIds");
        boolean asignado = usuarioService.asignarEspecialidades(usuarioId, especialidadIds);

        if (asignado) {
            return ResponseEntity.ok(Map.of("message", "Especialidades asignadas correctamente"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "No se pudieron asignar las especialidades"));
        }
    }




/////////////////////////////////////////////////////////////////////////////////////////////////////

    // Obtener horarios disponibles para una especialidad específica
    @GetMapping("/{usuarioId}/especialidades/{especialidadId}/horarios")
    public ResponseEntity<List<Horario>> obtenerHorariosPorEspecialidad(
            @PathVariable Long usuarioId,
            @PathVariable Long especialidadId) {

        // Verificar si el usuario tiene la especialidad asignada
        Usuario usuario = usuarioService.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (!usuario.getEspecialidades().stream().anyMatch(e -> e.getId().equals(especialidadId))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Collections.emptyList());
        }

        List<Horario> horarios = usuarioService.obtenerHorariosPorEspecialidad(especialidadId);
        return ResponseEntity.ok(horarios);
    }

    // Asignar horarios seleccionados al médico
    @PostMapping("/{usuarioId}/horarios")
    public ResponseEntity<Map<String, Object>> asignarHorariosAMedico(
            @PathVariable Long usuarioId,
            @RequestBody Map<String, List<Long>> horarioData) {

        List<Long> horarioIds = horarioData.get("horarioIds");
        boolean asignado = usuarioService.asignarHorariosAMedico(usuarioId, horarioIds);

        if (asignado) {
            return ResponseEntity.ok(Map.of("message", "Horarios asignados correctamente"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "No se pudieron asignar los horarios"));
        }
    }



    @GetMapping("/especialidades")
    public ResponseEntity<List<Especialidad>> obtenerEspecialidadesDelMedico(@RequestHeader("Authorization") String token) {
        Long usuarioId = jwtService.extractUserId(token.substring(7)); // Método para extraer el ID del usuario desde el token
        List<Especialidad> especialidades = usuarioService.obtenerEspecialidadesPorUsuario(usuarioId);
        return ResponseEntity.ok(especialidades);
    }










}
