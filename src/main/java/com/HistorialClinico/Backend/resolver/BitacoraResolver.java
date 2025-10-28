package com.HistorialClinico.Backend.resolver;

import com.HistorialClinico.Backend.model.Bitacora;
import com.HistorialClinico.Backend.service.BitacoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BitacoraResolver {

    @Autowired
    private BitacoraService bitacoraService;

    // ==================== QUERIES ====================

    @QueryMapping
    public List<Bitacora> bitacoras() {
        // BitacoraService no tiene método para obtener todas
        // Por ahora retornamos lista vacía
        throw new UnsupportedOperationException("Método no implementado. BitacoraService no expone método para listar todas las bitácoras.");
    }
}
