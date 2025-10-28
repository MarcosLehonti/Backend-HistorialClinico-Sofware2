package com.HistorialClinico.Backend.resolver;

import com.HistorialClinico.Backend.dto.TriajeDTO;
import com.HistorialClinico.Backend.dto.TriajeResponseDTO;
import com.HistorialClinico.Backend.model.Triaje;
import com.HistorialClinico.Backend.service.TriajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

@Controller
public class TriajeResolver {

    @Autowired
    private TriajeService triajeService;

    // ==================== QUERIES ====================

    @QueryMapping
    public List<TriajeResponseDTO> triajes() {
        return triajeService.obtenerTodosLosTriajes();
    }

    @QueryMapping
    public List<TriajeResponseDTO> triajesPorPaciente(@Argument Long pacienteId) {
        return triajeService.obtenerTriajesPorPacienteId(pacienteId);
    }

    // ==================== MUTATIONS ====================

    @MutationMapping
    public Triaje crearTriaje(@Argument Map<String, Object> input) {
        TriajeDTO triajeDTO = new TriajeDTO();
        triajeDTO.setPacienteId(Long.valueOf(input.get("pacienteId").toString()));
        triajeDTO.setEnfermeraId(Long.valueOf(input.get("enfermeraId").toString()));
        triajeDTO.setTemperatura(Double.valueOf(input.get("temperatura").toString()));
        triajeDTO.setFrecuenciaCardiaca(Integer.valueOf(input.get("frecuenciaCardiaca").toString()));
        triajeDTO.setFrecuenciaRespiratoria(Integer.valueOf(input.get("frecuenciaRespiratoria").toString()));
        triajeDTO.setSaturacionOxigeno(Integer.valueOf(input.get("saturacionOxigeno").toString()));
        triajeDTO.setPeso(Double.valueOf(input.get("peso").toString()));
        triajeDTO.setEstatura(Double.valueOf(input.get("estatura").toString()));
        
        if (input.containsKey("alergias")) {
            triajeDTO.setAlergias(input.get("alergias").toString());
        }
        if (input.containsKey("enfermedadesCronicas")) {
            triajeDTO.setEnfermedadesCronicas(input.get("enfermedadesCronicas").toString());
        }
        if (input.containsKey("motivoConsulta")) {
            triajeDTO.setMotivoConsulta(input.get("motivoConsulta").toString());
        }
        
        return triajeService.crearTriaje(triajeDTO);
    }
}
