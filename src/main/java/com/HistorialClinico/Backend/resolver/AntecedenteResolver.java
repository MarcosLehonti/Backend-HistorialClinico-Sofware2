package com.HistorialClinico.Backend.resolver;

import com.HistorialClinico.Backend.dto.AntecedenteRequestDTO;
import com.HistorialClinico.Backend.model.Antecedente;
import com.HistorialClinico.Backend.service.AntecedenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

@Controller
public class AntecedenteResolver {

    @Autowired
    private AntecedenteService antecedenteService;

    // ==================== QUERIES ====================

    @QueryMapping
    public List<Antecedente> antecedentesPorPaciente(@Argument Long pacienteId) {
        return antecedenteService.obtenerAntecedentesPorPacienteId(pacienteId);
    }

    @QueryMapping
    public List<Antecedente> antecedentesPorUsuario(@Argument Long usuarioId) {
        return antecedenteService.obtenerAntecedentesPorUsuarioId(usuarioId);
    }

    // ==================== MUTATIONS ====================

    @MutationMapping
    public Antecedente crearAntecedente(@Argument Map<String, Object> input) {
        AntecedenteRequestDTO antecedenteDTO = new AntecedenteRequestDTO();
        antecedenteDTO.setUsuarioId(Long.valueOf(input.get("usuarioId").toString()));
        antecedenteDTO.setPacienteId(Long.valueOf(input.get("pacienteId").toString()));
        antecedenteDTO.setEspecialidadId(Long.valueOf(input.get("especialidadId").toString()));
        antecedenteDTO.setDescripcion(input.get("descripcion").toString());
        
        return antecedenteService.crearAntecedente(antecedenteDTO);
    }
}
