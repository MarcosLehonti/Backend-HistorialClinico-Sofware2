package com.HistorialClinico.Backend.service;

import com.HistorialClinico.Backend.model.Especialidad;
import com.HistorialClinico.Backend.model.Horario;
import com.HistorialClinico.Backend.model.Usuario;
import com.HistorialClinico.Backend.repository.EspecialidadRepository;
import com.HistorialClinico.Backend.repository.HorarioRepository; // Importa el HorarioRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EspecialidadService {

    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Autowired
    private HorarioRepository horarioRepository; // Inyecta el HorarioRepository aquí

    public List<Especialidad> getAllEspecialidades() {
        return especialidadRepository.findAll();
    }

    public Optional<Especialidad> getEspecialidadById(Long id) {
        return especialidadRepository.findById(id);
    }

    public Especialidad saveEspecialidad(Especialidad especialidad) {
        return especialidadRepository.save(especialidad);
    }

    public Optional<Especialidad> updateEspecialidad(Long id, Especialidad especialidadActualizada) {
        return especialidadRepository.findById(id).map(especialidad -> {
            especialidad.setNombre(especialidadActualizada.getNombre());
            return especialidadRepository.save(especialidad);
        });
    }

    public boolean deleteEspecialidad(Long id) {
        if (especialidadRepository.existsById(id)) {
            especialidadRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Método para verificar si una especialidad existe por nombre
    public boolean existsByNombre(String nombre) {
        return especialidadRepository.existsByNombre(nombre);
    }

    public List<Usuario> getMedicosPorEspecialidad(Long especialidadId) {
        return especialidadRepository.findMedicosByEspecialidadId(especialidadId, "ROLE_MEDICO");
    }

    // Método para asignar un horario a una especialidad
    public void asignarHorarioAEspecialidad(Long especialidadId, Long horarioId) {
        Especialidad especialidad = especialidadRepository.findById(especialidadId)
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada con id: " + especialidadId));

        Horario horario = horarioRepository.findById(horarioId)
                .orElseThrow(() -> new RuntimeException("Horario no encontrado con id: " + horarioId));

        // Verificar si el horario ya está asignado a la especialidad
        if (especialidad.getHorarios().contains(horario)) {
            throw new IllegalArgumentException("El horario ya está asignado a esta especialidad.");
        }

        // Asignar el horario a la especialidad
        especialidad.getHorarios().add(horario);
        especialidadRepository.save(especialidad);
    }


    // Método para obtener todas las especialidades
    public List<Especialidad> obtenerTodasEspecialidades() {
        return especialidadRepository.findAll();
    }


}
