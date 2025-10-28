package com.HistorialClinico.Backend.service;

import com.HistorialClinico.Backend.dto.UsuarioEspecialidadResponse;
import com.HistorialClinico.Backend.model.Horario;
import com.HistorialClinico.Backend.model.UsuarioEspecialidadDia;
import com.HistorialClinico.Backend.repository.HorarioRepository;
import com.HistorialClinico.Backend.repository.UsuarioEspecialidadDiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioEspecialidadDiaService {

    @Autowired
    private UsuarioEspecialidadDiaRepository usuarioEspecialidadDiaRepository;
    private final HorarioRepository horarioRepository;

    public UsuarioEspecialidadDiaService(UsuarioEspecialidadDiaRepository usuarioEspecialidadDiaRepository, HorarioRepository horarioRepository) {
        this.usuarioEspecialidadDiaRepository = usuarioEspecialidadDiaRepository;
        this.horarioRepository = horarioRepository;
    }


    public UsuarioEspecialidadDia asignarEspecialidadDia(UsuarioEspecialidadDia usuarioEspecialidadDia) {
        return usuarioEspecialidadDiaRepository.save(usuarioEspecialidadDia);
    }

    public boolean existeAsignacion(Long usuarioId, Long especialidadId, Long turnoId, Long diaId) {
        return usuarioEspecialidadDiaRepository.existsByUsuarioIdAndEspecialidadIdAndTurnoIdAndDiaId(
                usuarioId, especialidadId, turnoId, diaId);
    }

    public List<UsuarioEspecialidadDia> obtenerAsignacionesPorEspecialidad(Long especialidadId) {
        return usuarioEspecialidadDiaRepository.findByEspecialidadId(especialidadId);
    }


    // Método para obtener los horarios basados en el turno_id
    public List<Horario> obtenerHorariosPorTurno(Long turnoId) {
        return horarioRepository.findByTurnoId(turnoId);
    }


}
