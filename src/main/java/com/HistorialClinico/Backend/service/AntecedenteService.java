package com.HistorialClinico.Backend.service;

import com.HistorialClinico.Backend.dto.AntecedenteRequestDTO;
import com.HistorialClinico.Backend.model.Antecedente;
import com.HistorialClinico.Backend.model.Usuario;
import com.HistorialClinico.Backend.model.Especialidad;
import com.HistorialClinico.Backend.repository.AntecedenteRepository;
import com.HistorialClinico.Backend.repository.UsuarioRepository;
import com.HistorialClinico.Backend.repository.EspecialidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AntecedenteService {

    @Autowired
    private AntecedenteRepository antecedenteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EspecialidadRepository especialidadRepository;

    public Antecedente crearAntecedente(AntecedenteRequestDTO antecedenteDTO) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(antecedenteDTO.getUsuarioId());
        Optional<Usuario> pacienteOpt = usuarioRepository.findById(antecedenteDTO.getPacienteId());
        Optional<Especialidad> especialidadOpt = especialidadRepository.findById(antecedenteDTO.getEspecialidadId());

        if (usuarioOpt.isPresent() && pacienteOpt.isPresent() && especialidadOpt.isPresent()) {
            Antecedente antecedente = new Antecedente(
                    usuarioOpt.get(),
                    pacienteOpt.get(),
                    especialidadOpt.get(),
                    antecedenteDTO.getDescripcion(),
                    antecedenteDTO.getFecha()
            );

            return antecedenteRepository.save(antecedente);
        } else {
            throw new RuntimeException("Usuario, paciente o especialidad no encontrado");
        }
    }


    public List<Antecedente> obtenerAntecedentesPorUsuarioId(Long usuarioId) {
        return antecedenteRepository.findByUsuarioId(usuarioId);
    }


    // Método para obtener antecedentes por pacienteId
    public List<Antecedente> obtenerAntecedentesPorPacienteId(Long pacienteId) {
        return antecedenteRepository.findByPacienteId(pacienteId);
    }
}
