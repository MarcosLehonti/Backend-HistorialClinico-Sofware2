package com.HistorialClinico.Backend.resolver;

import com.HistorialClinico.Backend.dto.CitaDTO;
import com.HistorialClinico.Backend.model.Cita;
import com.HistorialClinico.Backend.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

@Controller
public class CitaResolver {

    @Autowired
    private CitaService citaService;

    // ==================== QUERIES ====================

    @QueryMapping
    public List<Cita> citasPorUsuario(@Argument Long usuarioId) {
        return citaService.obtenerCitasPorUsuario(usuarioId);
    }

    @QueryMapping
    public List<Cita> citasPorMedico(@Argument Long medicoId) {
        return citaService.obtenerCitasPorMedico(medicoId);
    }

    // ==================== MUTATIONS ====================

    @MutationMapping
    public Cita crearCita(@Argument Map<String, Object> input) {
        CitaDTO citaDTO = new CitaDTO();
        citaDTO.setUsuarioId(Long.valueOf(input.get("usuarioId").toString()));
        citaDTO.setMedicoId(Long.valueOf(input.get("medicoId").toString()));
        citaDTO.setEspecialidadId(Long.valueOf(input.get("especialidadId").toString()));
        citaDTO.setTurnoId(Long.valueOf(input.get("turnoId").toString()));
        citaDTO.setDiaId(Long.valueOf(input.get("diaId").toString()));
        citaDTO.setHorarioId(Long.valueOf(input.get("horarioId").toString()));
        citaDTO.setNombreUsuarioLogeado(input.get("nombreUsuarioLogeado").toString());
        
        return citaService.crearCita(citaDTO);
    }
}
