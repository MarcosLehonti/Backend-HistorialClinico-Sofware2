package com.HistorialClinico.Backend.service;

import com.HistorialClinico.Backend.model.Turno;
import com.HistorialClinico.Backend.repository.TurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurnoService {

    @Autowired
    private TurnoRepository turnoRepository;

    // Método para obtener todos los turnos
    public List<Turno> obtenerTodosTurnos() {
        return turnoRepository.findAll();
    }
}
