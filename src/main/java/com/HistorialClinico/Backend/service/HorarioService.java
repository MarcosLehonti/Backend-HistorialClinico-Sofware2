package com.HistorialClinico.Backend.service;

import com.HistorialClinico.Backend.model.Horario;
import com.HistorialClinico.Backend.model.Usuario;
import com.HistorialClinico.Backend.repository.HorarioRepository;
import com.HistorialClinico.Backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class HorarioService {

    @Autowired
    private HorarioRepository horarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Crear un nuevo horario
    public Horario crearHorario(Horario horario) {
        return horarioRepository.save(horario);
    }

    // Obtener todos los horarios
    public List<Horario> obtenerHorarios() {
        return horarioRepository.findAll();
    }

    // Obtener un horario por ID
    public Horario obtenerHorarioPorId(Long id) {
        return horarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Horario no encontrado con id: " + id));
    }

//    // Actualizar un horario existente
//    public Horario actualizarHorario(Long id, Horario horarioDetalles) {
//        Horario horario = obtenerHorarioPorId(id);
//        horario.setTimeSlot(horarioDetalles.getTimeSlot());
//        horario.setTurnoId(horarioDetalles.getTurnoId());
//
//        return horarioRepository.save(horario);
//    }

    // Eliminar un horario
    public void eliminarHorario(Long id) {
        Horario horario = obtenerHorarioPorId(id);
        horarioRepository.delete(horario);
    }

    // Asignar un horario a un médico
    public void asignarHorarioAMedico(Long medicoId, Long horarioId) {
        Usuario medico = usuarioRepository.findById(medicoId)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado con id: " + medicoId));

        // Verificar si el usuario tiene el rol de MEDICO
        boolean esMedico = medico.getRoles().stream()
                .anyMatch(rol -> rol.getNombre().equals("ROLE_MEDICO"));

        if (!esMedico) {
            throw new IllegalArgumentException("El usuario con id: " + medicoId + " no es un médico.");
        }

        Horario horario = horarioRepository.findById(horarioId)
                .orElseThrow(() -> new RuntimeException("Horario no encontrado con id: " + horarioId));

        // Verificar si ya está asignado
        if (medico.getHorarios().contains(horario)) {
            throw new IllegalArgumentException("El horario ya está asignado a este médico.");
        }

        // Asignar horario
        medico.addHorario(horario);
        usuarioRepository.save(medico);
    }

    // Obtener horarios por médico
    public Set<Horario> obtenerHorariosPorMedico(Long medicoId) {
        Usuario medico = usuarioRepository.findById(medicoId)
                .orElseThrow(() -> new RuntimeException("Médico no encontrado con id: " + medicoId));

        // Verificar si el usuario tiene el rol de MEDICO
        boolean esMedico = medico.getRoles().stream()
                .anyMatch(rol -> rol.getNombre().equals("ROLE_MEDICO"));

        if (!esMedico) {
            throw new IllegalArgumentException("El usuario con id: " + medicoId + " no es un médico.");
        }

        return medico.getHorarios();
    }
}
