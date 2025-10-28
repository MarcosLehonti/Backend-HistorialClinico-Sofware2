package com.HistorialClinico.Backend.service;

import com.HistorialClinico.Backend.model.Bitacora;
import com.HistorialClinico.Backend.repository.BitacoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BitacoraService {

    @Autowired
    private BitacoraRepository bitacoraRepository;

    public void registrar(String usuario, String accion, String detalles) {
        Bitacora bitacora = new Bitacora();
        bitacora.setUsuario(usuario);
        bitacora.setAccion(accion);
        bitacora.setDetalles(detalles);
        bitacora.setFecha(new Date());
        bitacoraRepository.save(bitacora);
    }
}
