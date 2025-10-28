package com.HistorialClinico.Backend.resolver;

import com.HistorialClinico.Backend.model.Especialidad;
import com.HistorialClinico.Backend.service.EspecialidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class EspecialidadResolver {

    @Autowired
    private EspecialidadService especialidadService;

    @QueryMapping
    public List<Especialidad> especialidades() {
        return especialidadService.getAllEspecialidades();
    }

    @QueryMapping
    public Especialidad especialidad(@Argument Long id) {
        return especialidadService.getEspecialidadById(id).orElse(null);
    }

    @MutationMapping
    public Especialidad crearEspecialidad(@Argument String nombre) {
        Especialidad especialidad = new Especialidad();
        especialidad.setNombre(nombre);
        return especialidadService.saveEspecialidad(especialidad);
    }
}
