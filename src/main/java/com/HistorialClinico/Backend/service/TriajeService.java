package com.HistorialClinico.Backend.service;


import com.HistorialClinico.Backend.dto.TriajeDTO;
import com.HistorialClinico.Backend.dto.TriajeResponseDTO;
import com.HistorialClinico.Backend.model.Triaje;
import com.HistorialClinico.Backend.model.Usuario;
import com.HistorialClinico.Backend.repository.TriajeRepository;
import com.HistorialClinico.Backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TriajeService {

    private final TriajeRepository triajeRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public TriajeService(TriajeRepository triajeRepository, UsuarioRepository usuarioRepository) {
        this.triajeRepository = triajeRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Triaje crearTriaje(TriajeDTO triajeDTO) {
        Usuario paciente = usuarioRepository.findById(triajeDTO.getPacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
        Usuario enfermera = usuarioRepository.findById(triajeDTO.getEnfermeraId())
                .orElseThrow(() -> new RuntimeException("Enfermera no encontrada"));

        Triaje triaje = new Triaje();
        triaje.setPaciente(paciente);
        triaje.setEnfermera(enfermera);
        triaje.setTemperatura(triajeDTO.getTemperatura());
        triaje.setFecha(triajeDTO.getFecha());
        triaje.setHora(triajeDTO.getHora());
        triaje.setFrecuenciaCardiaca(triajeDTO.getFrecuenciaCardiaca());
        triaje.setFrecuenciaRespiratoria(triajeDTO.getFrecuenciaRespiratoria());
        triaje.setSaturacionOxigeno(triajeDTO.getSaturacionOxigeno());
        triaje.setPeso(triajeDTO.getPeso());
        triaje.setEstatura(triajeDTO.getEstatura());
        triaje.setAlergias(triajeDTO.getAlergias());
        triaje.setEnfermedadesCronicas(triajeDTO.getEnfermedadesCronicas());
        triaje.setMotivoConsulta(triajeDTO.getMotivoConsulta());

        return triajeRepository.save(triaje);
    }




    public List<TriajeResponseDTO> obtenerTodosLosTriajes() {
        return triajeRepository.findAll().stream().map(triaje -> {
            TriajeResponseDTO dto = new TriajeResponseDTO();
            dto.setId(triaje.getId());
            dto.setNombrePaciente(triaje.getPaciente().getUsername()); // Obtener nombre del paciente
            dto.setNombreEnfermera(triaje.getEnfermera().getUsername()); // Obtener nombre de la enfermera
            dto.setTemperatura(triaje.getTemperatura());
            dto.setFecha(triaje.getFecha());
            dto.setHora(triaje.getHora());
            dto.setFrecuenciaCardiaca(triaje.getFrecuenciaCardiaca());
            dto.setFrecuenciaRespiratoria(triaje.getFrecuenciaRespiratoria());
            dto.setSaturacionOxigeno(triaje.getSaturacionOxigeno());
            dto.setPeso(triaje.getPeso());
            dto.setEstatura(triaje.getEstatura());
            dto.setAlergias(triaje.getAlergias());
            dto.setEnfermedadesCronicas(triaje.getEnfermedadesCronicas());
            dto.setMotivoConsulta(triaje.getMotivoConsulta());
            return dto;
        }).collect(Collectors.toList());
    }



    public List<TriajeResponseDTO> obtenerTriajesPorPacienteId(Long pacienteId) {
        return triajeRepository.findByPacienteId(pacienteId).stream().map(triaje -> {
            TriajeResponseDTO dto = new TriajeResponseDTO();
            dto.setId(triaje.getId());
            dto.setNombrePaciente(triaje.getPaciente().getUsername()); // Nombre del paciente
            dto.setNombreEnfermera(triaje.getEnfermera().getUsername()); // Nombre de la enfermera
            dto.setTemperatura(triaje.getTemperatura());
            dto.setFecha(triaje.getFecha());
            dto.setHora(triaje.getHora());
            dto.setFrecuenciaCardiaca(triaje.getFrecuenciaCardiaca());
            dto.setFrecuenciaRespiratoria(triaje.getFrecuenciaRespiratoria());
            dto.setSaturacionOxigeno(triaje.getSaturacionOxigeno());
            dto.setPeso(triaje.getPeso());
            dto.setEstatura(triaje.getEstatura());
            dto.setAlergias(triaje.getAlergias());
            dto.setEnfermedadesCronicas(triaje.getEnfermedadesCronicas());
            dto.setMotivoConsulta(triaje.getMotivoConsulta());
            return dto;
        }).collect(Collectors.toList());
    }
}
