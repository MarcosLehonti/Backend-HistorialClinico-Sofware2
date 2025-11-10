package com.HistorialClinico.Backend.resolver;

import com.HistorialClinico.Backend.dto.UsuarioEspecialidadDiaDTO;
import com.HistorialClinico.Backend.repository.UsuarioEspecialidadDiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import java.util.List;

@Controller
public class UsuarioEspecialidadResolver {

    @Autowired
    private UsuarioEspecialidadDiaRepository usuarioEspecialidadDiaRepository;

    @QueryMapping
    public List<UsuarioEspecialidadDiaDTO> usuariosPorEspecialidad(@Argument Long especialidadId) {
        return usuarioEspecialidadDiaRepository.obtenerUsuariosYHorariosPorEspecialidad(especialidadId);
    }
}
