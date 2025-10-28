//package com.HistorialClinico.Backend.config;
//
//import com.HistorialClinico.Backend.model.Especialidad;
//import com.HistorialClinico.Backend.model.Horario;
//import com.HistorialClinico.Backend.service.EspecialidadService;
//import com.HistorialClinico.Backend.service.HorarioService;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.time.LocalTime;
//import java.util.Arrays;
//import java.util.List;
//
//@Configuration
//public class DataInitializer {
//
//    private final EspecialidadService especialidadService;
//    private final HorarioService horarioService;
//
//    public DataInitializer(EspecialidadService especialidadService, HorarioService horarioService) {
//        this.especialidadService = especialidadService;
//        this.horarioService = horarioService;
//    }
//
//    @Bean
//    public CommandLineRunner run() {
//        return args -> {
//            // Crear especialidades por defecto si no existen
//            List<String> especialidadesPorDefecto = Arrays.asList(
//                    "Pediatría", "Medicina General", "Laboratorios", "Psicología", "Dermatología", "Cardiología"
//            );
//
//            for (String nombre : especialidadesPorDefecto) {
//                if (!especialidadService.existsByNombre(nombre)) {
//                    Especialidad especialidad = new Especialidad();
//                    especialidad.setNombre(nombre);
//                    especialidadService.saveEspecialidad(especialidad);
//                }
//            }
//
//            // Crear horarios por defecto si no existen
//            LocalTime horaInicio = LocalTime.of(8, 0);
//            LocalTime horaFin = LocalTime.of(12, 0);
//            int intervaloMinutos = 15;
//
//            while (horaInicio.isBefore(horaFin)) {
//                if (!horarioService.existsByHora(horaInicio)) {
//                    Horario horario = new Horario();
//                    horario.setHora(horaInicio);
//                    horarioService.saveHorario(horario);
//                }
//                horaInicio = horaInicio.plusMinutes(intervaloMinutos);
//            }
//        };
//    }
//}

package com.HistorialClinico.Backend.config;

import com.HistorialClinico.Backend.model.Especialidad;
import com.HistorialClinico.Backend.model.Rol;
import com.HistorialClinico.Backend.model.Usuario;
import com.HistorialClinico.Backend.service.EspecialidadService;
import com.HistorialClinico.Backend.service.HorarioService;
import com.HistorialClinico.Backend.service.RolService;
import com.HistorialClinico.Backend.service.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

@Configuration
public class DataInitializer {

    private final EspecialidadService especialidadService;
    private final HorarioService horarioService;
    private final RolService rolService;
    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(EspecialidadService especialidadService, HorarioService horarioService, RolService rolService, UsuarioService usuarioService, PasswordEncoder passwordEncoder) {
        this.especialidadService = especialidadService;
        this.horarioService = horarioService;
        this.rolService = rolService;
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public CommandLineRunner run() {
        return args -> {
            // Crear especialidades por defecto si no existen
            List<String> especialidadesPorDefecto = Arrays.asList(
                    "Pediatría", "Medicina General", "Laboratorios", "Psicología", "Dermatología", "Cardiología"
            );

            for (String nombre : especialidadesPorDefecto) {
                if (!especialidadService.existsByNombre(nombre)) {
                    Especialidad especialidad = new Especialidad();
                    especialidad.setNombre(nombre);
                    especialidadService.saveEspecialidad(especialidad);
                }
            }

            // Crear roles por defecto si no existen
            List<String> rolesPorDefecto = List.of("ROLE_ADMIN", "ROLE_ENFERMERA", "ROLE_MEDICO", "ROLE_USER");

            for (String nombre : rolesPorDefecto) {
                rolService.findByNombre(nombre).orElseGet(() -> rolService.saveRol(new Rol(nombre)));
            }


        };
    }
}
