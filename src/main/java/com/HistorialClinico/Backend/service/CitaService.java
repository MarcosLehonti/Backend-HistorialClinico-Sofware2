package com.HistorialClinico.Backend.service;

import com.HistorialClinico.Backend.dto.CitaDTO;
import com.HistorialClinico.Backend.dto.CitaResponseDTO;
import com.HistorialClinico.Backend.dto.EmailNotificationDTO;
import com.HistorialClinico.Backend.model.*;
import com.HistorialClinico.Backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Service
public class CitaService {

    private final CitaRepository citaRepository;
    private final UsuarioRepository usuarioRepository;
    private final EspecialidadRepository especialidadRepository;
    private final TurnoRepository turnoRepository;
    private final DiaRepository diaRepository;
    private final HorarioRepository horarioRepository;
    private final EmailNotificationService emailNotificationService;

    @Autowired
    public CitaService(CitaRepository citaRepository,
                       UsuarioRepository usuarioRepository,
                       EspecialidadRepository especialidadRepository,
                       TurnoRepository turnoRepository,
                       DiaRepository diaRepository,
                       HorarioRepository horarioRepository,
                       EmailNotificationService emailNotificationService) {
        this.citaRepository = citaRepository;
        this.usuarioRepository = usuarioRepository;
        this.especialidadRepository = especialidadRepository;
        this.turnoRepository = turnoRepository;
        this.diaRepository = diaRepository;
        this.horarioRepository = horarioRepository;
        this.emailNotificationService = emailNotificationService;
    }

    public Cita crearCita(CitaDTO citaDTO) {
        Cita cita = new Cita();

        // Configuración de otros atributos de Cita
        Usuario usuario = usuarioRepository.findById(citaDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Usuario medico = usuarioRepository.findById(citaDTO.getMedicoId())
                .orElseThrow(() -> new RuntimeException("Médico no encontrado"));
        Especialidad especialidad = especialidadRepository.findById(citaDTO.getEspecialidadId())
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
        Turno turno = turnoRepository.findById(citaDTO.getTurnoId())
                .orElseThrow(() -> new RuntimeException("Turno no encontrado"));
        Dia dia = diaRepository.findById(citaDTO.getDiaId())
                .orElseThrow(() -> new RuntimeException("Día no encontrado"));
        Horario horario = horarioRepository.findById(citaDTO.getHorarioId())
                .orElseThrow(() -> new RuntimeException("Horario no encontrado"));

        // Asignación de atributos
        cita.setUsuario(usuario);
        cita.setMedico(medico);
        cita.setEspecialidad(especialidad);
        cita.setTurno(turno);
        cita.setDia(dia);
        cita.setHorario(horario.getTimeSlot().toString()); // Almacena el horario en texto
        cita.setNombreUsuarioLogeado(citaDTO.getNombreUsuarioLogeado());
        cita.setHorarioSeleccionado(horario); // Asigna el objeto Horario

        // Cambiar disponibilidad del horario a false
        horario.setDisponibilidad(false);
        horarioRepository.save(horario); // Guardar el cambio en la base de datos

        // Guardar la cita
        Cita citaGuardada = citaRepository.save(cita);
        
        // Enviar notificación por email al paciente
        enviarNotificacionCita(citaGuardada, usuario, medico, especialidad, horario);
        
        return citaGuardada;
    }



    public List<CitaResponseDTO> obtenerCitasPorUsuarioId(Long usuarioId) {
        return citaRepository.findCitasByUsuarioId(usuarioId);
    }
    
    public List<Cita> obtenerCitasPorUsuario(Long usuarioId) {
        return citaRepository.findByUsuarioId(usuarioId);
    }

    public List<Cita> obtenerCitasPorMedico(Long medicoId) {
        return citaRepository.findByMedicoId(medicoId);
    }


    public List<CitaResponseDTO> obtenerCitasPorMedicoId(Long medicoId) {
        return citaRepository.findCitasByMedicoId(medicoId);
    }
    
    /**
     * Envía notificación de confirmación de cita al email del paciente
     */
    private void enviarNotificacionCita(Cita cita, Usuario paciente, Usuario medico, 
                                        Especialidad especialidad, Horario horario) {
        try {
            // Formatear la fecha en español
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy", 
                                                                      new Locale("es", "BO"));
            String fechaFormateada = horario.getFecha().format(formatter);
            
            // Crear DTO con los datos del email
            EmailNotificationDTO emailData = EmailNotificationDTO.forAppointmentConfirmation(
                paciente.getEmail(),
                paciente.getUsername(),
                fechaFormateada,
                cita.getHorario(),
                medico.getUsername(),
                especialidad.getNombre(),
                cita.getNombreUsuarioLogeado()
            );
            
            // Enviar email de forma asíncrona (no bloquea el flujo principal)
            emailNotificationService.sendAppointmentConfirmation(emailData);
            
        } catch (Exception e) {
            // Log del error pero no interrumpir el flujo
            System.err.println("Error al enviar notificación de cita: " + e.getMessage());
        }
    }
}
