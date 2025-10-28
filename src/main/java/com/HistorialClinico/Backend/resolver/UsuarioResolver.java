package com.HistorialClinico.Backend.resolver;

import com.HistorialClinico.Backend.model.Especialidad;
import com.HistorialClinico.Backend.model.Horario;
import com.HistorialClinico.Backend.model.Rol;
import com.HistorialClinico.Backend.model.Usuario;
import com.HistorialClinico.Backend.service.BitacoraService;
import com.HistorialClinico.Backend.service.JwtService;
import com.HistorialClinico.Backend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.ContextValue;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class UsuarioResolver {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private BitacoraService bitacoraService;

    @Autowired
    private JwtService jwtService;

    // ==================== QUERIES ====================

    @QueryMapping
    public List<Usuario> usuarios() {
        return usuarioService.findAllUsuarios();
    }

    @QueryMapping
    public Usuario usuario(@Argument Long id) {
        return usuarioService.findById(id).orElse(null);
    }

    @QueryMapping
    public Usuario usuarioPorUsername(@Argument String username) {
        return usuarioService.findByUsername(username).orElse(null);
    }

    @QueryMapping
    public List<Usuario> medicos() {
        return usuarioService.obtenerMedicos();
    }

    @QueryMapping
    public Usuario perfil(@ContextValue String token) {
        String username = jwtService.extractUsername(token);
        return usuarioService.findByUsername(username).orElse(null);
    }

    @QueryMapping
    public List<Rol> roles() {
        return usuarioService.obtenerTodosRoles();
    }

    @QueryMapping
    public List<Especialidad> especialidadesPorUsuario(@Argument Long usuarioId) {
        return usuarioService.obtenerEspecialidadesPorUsuario(usuarioId);
    }

    @QueryMapping
    public List<Horario> horariosPorEspecialidad(@Argument Long usuarioId, @Argument Long especialidadId) {
        Usuario usuario = usuarioService.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        if (!usuario.getEspecialidades().stream().anyMatch(e -> e.getId().equals(especialidadId))) {
            throw new RuntimeException("Usuario no tiene esta especialidad asignada");
        }
        
        return usuarioService.obtenerHorariosPorEspecialidad(especialidadId);
    }

    // ==================== MUTATIONS ====================

    @MutationMapping
    public Map<String, Object> login(@Argument Map<String, String> input) {
        String username = input.get("username");
        String password = input.get("password");
        
        Optional<Usuario> usuario = usuarioService.findByUsername(username);
        Map<String, Object> response = new HashMap<>();

        if (usuario.isPresent() && usuarioService.checkPassword(password, usuario.get().getPassword())) {
            String token = jwtService.generateToken(usuario.get());
            String rol = usuario.get().getRoles().stream()
                    .findFirst()
                    .map(Rol::getNombre)
                    .orElse("ROLE_USER");

            response.put("token", token);
            response.put("usuarioId", usuario.get().getId());
            response.put("rol", rol);

            // Registro de bitácora
            bitacoraService.registrar(
                    usuario.get().getUsername(),
                    "Inicio de Sesion",
                    "Inicio de Sesion Exitoso para el usuario de rol " + rol
            );

            return response;
        } else {
            throw new RuntimeException("Credenciales incorrectas");
        }
    }

    @MutationMapping
    public Usuario registro(@Argument Map<String, String> input) {
        Usuario usuario = new Usuario();
        usuario.setUsername(input.get("username"));
        usuario.setPassword(input.get("password"));
        usuario.setEmail(input.get("email"));
        
        Usuario nuevoUsuario = usuarioService.saveUsuario(usuario);
        
        // Registrar en la bitácora
        bitacoraService.registrar(
                "Administrador del sistema",
                "Registro de Nuevo Usuario",
                "Usuario creado con ID " + nuevoUsuario.getId() + " y nombre de usuario " + nuevoUsuario.getUsername()
        );
        
        return nuevoUsuario;
    }

    @MutationMapping
    public Usuario actualizarUsuario(@Argument Long id, @Argument Map<String, String> input) {
        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setUsername(input.get("username"));
        usuarioActualizado.setPassword(input.get("password"));
        usuarioActualizado.setEmail(input.get("email"));
        
        return usuarioService.updateUsuario(id, usuarioActualizado)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    @MutationMapping
    public Boolean eliminarUsuario(@Argument Long id) {
        return usuarioService.deleteUsuario(id);
    }

    @MutationMapping
    public Boolean asignarRol(@Argument Long usuarioId, @Argument String rol) {
        return usuarioService.asignarRol(usuarioId, rol);
    }

    @MutationMapping
    public Boolean asignarEspecialidades(@Argument Long usuarioId, @Argument List<Long> especialidadIds) {
        return usuarioService.asignarEspecialidades(usuarioId, especialidadIds);
    }

    @MutationMapping
    public Boolean asignarHorarios(@Argument Long usuarioId, @Argument List<Long> horarioIds) {
        return usuarioService.asignarHorariosAMedico(usuarioId, horarioIds);
    }
}
