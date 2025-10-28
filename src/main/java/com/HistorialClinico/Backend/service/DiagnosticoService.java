package com.HistorialClinico.Backend.service;

// src/main/java/com/HistorialClinico/Backend/service/DiagnosticoService.java

import com.HistorialClinico.Backend.dto.DiagnosticoDTO;
import com.HistorialClinico.Backend.dto.DiagnosticoResponseDTO;
import com.HistorialClinico.Backend.model.Diagnostico;
import com.HistorialClinico.Backend.model.Usuario;
import com.HistorialClinico.Backend.model.Especialidad;
import com.HistorialClinico.Backend.repository.DiagnosticoRepository;
import com.HistorialClinico.Backend.repository.UsuarioRepository;
import com.HistorialClinico.Backend.repository.EspecialidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiagnosticoService {

    private final DiagnosticoRepository diagnosticoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EspecialidadRepository especialidadRepository;

    @Autowired
    public DiagnosticoService(DiagnosticoRepository diagnosticoRepository,
                              UsuarioRepository usuarioRepository,
                              EspecialidadRepository especialidadRepository) {
        this.diagnosticoRepository = diagnosticoRepository;
        this.usuarioRepository = usuarioRepository;
        this.especialidadRepository = especialidadRepository;
    }

    public Diagnostico crearDiagnostico(DiagnosticoDTO diagnosticoDTO) {
        Usuario medico = usuarioRepository.findById(diagnosticoDTO.getMedicoId())
                .orElseThrow(() -> new RuntimeException("Médico no encontrado"));
        Usuario paciente = usuarioRepository.findById(diagnosticoDTO.getPacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        Especialidad especialidad = especialidadRepository.findById(diagnosticoDTO.getEspecialidadId())
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));

        Diagnostico diagnostico = new Diagnostico();
        diagnostico.setMedico(medico);
        diagnostico.setPaciente(paciente);
        diagnostico.setDescripcion(diagnosticoDTO.getDescripcion());
        diagnostico.setFecha(diagnosticoDTO.getFecha());
        diagnostico.setEspecialidad(especialidad);
        diagnostico.setTratamiento(diagnosticoDTO.getTratamiento());


        return diagnosticoRepository.save(diagnostico);
    }




    public List<DiagnosticoResponseDTO> obtenerDiagnosticosPorMedicoId(Long medicoId) {
        return diagnosticoRepository.findByMedicoId(medicoId)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public List<DiagnosticoResponseDTO> obtenerDiagnosticosPorPacienteId(Long pacienteId) {
        return diagnosticoRepository.findByPacienteId(pacienteId)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // Método auxiliar para convertir Diagnostico a DiagnosticoResponseDTO
    private DiagnosticoResponseDTO convertirADTO(Diagnostico diagnostico) {
        DiagnosticoResponseDTO dto = new DiagnosticoResponseDTO();
        dto.setId(diagnostico.getId());
        dto.setDescripcion(diagnostico.getDescripcion());
        dto.setFecha(diagnostico.getFecha());
        dto.setNombreMedico(diagnostico.getMedico().getUsername());
        dto.setNombrePaciente(diagnostico.getPaciente().getUsername());
        dto.setEspecialidad(diagnostico.getEspecialidad().getNombre());
        dto.setTratamiento(diagnostico.getTratamiento());
        return dto;
    }
}
