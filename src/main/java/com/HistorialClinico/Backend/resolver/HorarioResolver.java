package com.HistorialClinico.Backend.resolver;

import com.HistorialClinico.Backend.model.Horario;
import com.HistorialClinico.Backend.service.HorarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class HorarioResolver {

    @Autowired
    private HorarioService horarioService;

    // ==================== QUERIES ====================

    @QueryMapping
    public List<Horario> horarios() {
        return horarioService.obtenerHorarios();
    }

    @QueryMapping
    public List<Horario> horariosPorMedico(@Argument Long medicoId) {
        return horarioService.obtenerHorariosPorMedico(medicoId).stream().toList();
    }
}
