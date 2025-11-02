package com.HistorialClinico.Backend.service;

import com.HistorialClinico.Backend.model.Especialidad;
import com.HistorialClinico.Backend.model.Horario;
import com.HistorialClinico.Backend.model.Rol;
import com.HistorialClinico.Backend.model.Usuario;
import com.HistorialClinico.Backend.repository.EspecialidadRepository;
import com.HistorialClinico.Backend.repository.HorarioRepository;
import com.HistorialClinico.Backend.repository.RolRepository;
import com.HistorialClinico.Backend.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Autowired
    private HorarioRepository horarioRepository;


    public Usuario saveUsuario(Usuario usuario) {
        // Asignar rol ROLE_USER por defecto al nuevo usuario
        Rol rolUsuario = rolRepository.findByNombre("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Error: Rol 'ROLE_USER' no encontrado"));
        usuario.getRoles().add(rolUsuario);

        // Encriptar la contraseña
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
    public List<Usuario> findAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> updateUsuario(Long id, Usuario usuarioActualizado) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setUsername(usuarioActualizado.getUsername());
            usuario.setEmail(usuarioActualizado.getEmail());
            if (!usuarioActualizado.getPassword().isEmpty()) {
                usuario.setPassword(passwordEncoder.encode(usuarioActualizado.getPassword()));
            }
            return usuarioRepository.save(usuario);
        });
    }

    public boolean deleteUsuario(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Método para verificar contraseñas
    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public List<Usuario> obtenerTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    public List<Rol> obtenerTodosRoles() {
        return rolRepository.findAll();
    }

    public boolean asignarRol(Long usuarioId, String nombreRol) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Limpiar los roles actuales
        usuario.getRoles().clear();

        // Buscar el rol y extraer el valor
        Optional<Rol> optionalRol = rolRepository.findByNombre(nombreRol);

        if (optionalRol.isPresent()) {
            Rol rol = optionalRol.get();
            usuario.getRoles().add(rol);
            usuarioRepository.save(usuario);
            return true; // Rol asignado correctamente
        }

        return false; // Rol no encontrado o no asignado
    }

    public List<Usuario> obtenerMedicos() {
        return usuarioRepository.findByRolesNombreRol("ROLE_MEDICO");
    }



//    public boolean asignarEspecialidad(Long usuarioId, Long especialidadId) {
//        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
//        Optional<Especialidad> especialidadOpt = especialidadRepository.findById(especialidadId);
//
//        if (usuarioOpt.isPresent() && especialidadOpt.isPresent()) {
//            Usuario usuario = usuarioOpt.get();
//            Especialidad especialidad = especialidadOpt.get();
//            usuario.addEspecialidad(especialidad);
//            usuarioRepository.save(usuario);
//            return true;
//        }
//        return false;
//    }







    public boolean asignarEspecialidades(Long usuarioId, List<Long> especialidadIds) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            List<Especialidad> especialidades = especialidadRepository.findAllById(especialidadIds);
            usuario.getEspecialidades().addAll(especialidades);
            usuarioRepository.save(usuario);
            return true;
        }
        return false;
    }



    ////////////////////////////////////////////////////////////////////////////////////
    // Método para obtener horarios disponibles para una especialidad específica
    public List<Horario> obtenerHorariosPorEspecialidad(Long especialidadId) {
        Especialidad especialidad = especialidadRepository.findById(especialidadId)
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
        return new ArrayList<>(especialidad.getHorarios());
    }

    // Método para asignar horarios específicos a un médico
    @Transactional
    public boolean asignarHorariosAMedico(Long usuarioId, List<Long> horarioIds) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Horario> horarios = horarioRepository.findAllById(horarioIds);
        for (Horario horario : horarios) {
            usuario.addHorario(horario); // Añadimos el horario al médico
        }
        usuarioRepository.save(usuario);
        return true;
    }


    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }
//    public List<Especialidad> obtenerEspecialidadesPorUsuario(Long usuarioId) {
//        return usuarioRepository.findEspecialidadesByUsuarioId(usuarioId);
//    }


    public List<Especialidad> obtenerEspecialidadesPorUsuario(Long usuarioId) {
        return usuarioRepository.findEspecialidadesByUsuarioId(usuarioId);
    }

    public boolean existsByUsername(String username) {
        return usuarioRepository.existsByUsername(username);
    }



}
