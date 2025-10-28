package com.HistorialClinico.Backend.service;

import com.HistorialClinico.Backend.model.Dia;
import com.HistorialClinico.Backend.repository.DiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiaService {

    @Autowired
    private DiaRepository diaRepository;

    // Obtener todos los días
    public List<Dia> obtenerTodosLosDias() {
        return diaRepository.findAll();
    }

    // Obtener día por ID
    public Optional<Dia> obtenerDiaPorId(Long id) {
        return diaRepository.findById(id);
    }

    // Guardar un nuevo día
    public Dia guardarDia(Dia dia) {
        return diaRepository.save(dia);
    }

    // Actualizar un día existente
    public Optional<Dia> actualizarDia(Long id, Dia diaActualizado) {
        return diaRepository.findById(id).map(dia -> {
            dia.setNombre(diaActualizado.getNombre());
            return diaRepository.save(dia);
        });
    }

    // Eliminar un día por ID
    public boolean eliminarDia(Long id) {
        if (diaRepository.existsById(id)) {
            diaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
