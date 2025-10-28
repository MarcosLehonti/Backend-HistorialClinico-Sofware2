package com.HistorialClinico.Backend.resolver;

import com.HistorialClinico.Backend.dto.DiagnosticoDTO;
import com.HistorialClinico.Backend.dto.DiagnosticoResponseDTO;
import com.HistorialClinico.Backend.model.Diagnostico;
import com.HistorialClinico.Backend.service.DiagnosticoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

@Controller
public class DiagnosticoResolver {

    @Autowired
    private DiagnosticoService diagnosticoService;

    // ==================== QUERIES ====================

    @QueryMapping
    public List<DiagnosticoResponseDTO> diagnosticosPorPaciente(@Argument Long pacienteId) {
        return diagnosticoService.obtenerDiagnosticosPorPacienteId(pacienteId);
    }

    @QueryMapping
    public List<DiagnosticoResponseDTO> diagnosticosPorMedico(@Argument Long medicoId) {
        return diagnosticoService.obtenerDiagnosticosPorMedicoId(medicoId);
    }

    // ==================== MUTATIONS ====================

    @MutationMapping
    public Diagnostico crearDiagnostico(@Argument Map<String, Object> input) {
        DiagnosticoDTO diagnosticoDTO = new DiagnosticoDTO();
        diagnosticoDTO.setPacienteId(Long.valueOf(input.get("pacienteId").toString()));
        diagnosticoDTO.setMedicoId(Long.valueOf(input.get("medicoId").toString()));
        diagnosticoDTO.setEspecialidadId(Long.valueOf(input.get("especialidadId").toString()));
        diagnosticoDTO.setDescripcion(input.get("descripcion").toString());
        
        if (input.containsKey("tratamiento")) {
            diagnosticoDTO.setTratamiento(input.get("tratamiento").toString());
        }
        
        return diagnosticoService.crearDiagnostico(diagnosticoDTO);
    }
}
