package com.HistorialClinico.Backend.resolver;

import com.HistorialClinico.Backend.model.Rol;
import com.HistorialClinico.Backend.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class RolResolver {

    @Autowired
    private RolService rolService;

    // ==================== QUERIES ====================

    @QueryMapping
    public List<Rol> roles() {
        return rolService.findAllRoles();
    }
}
