package com.example.demo.models.service;

import com.example.demo.models.entity.ComentarioDB;
import com.example.demo.repository.ComentarioDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ComentarioDBServiceImpl implements ComentarioDBService {

    private final ComentarioDBRepository comentarioDBRepository;

    @Autowired
    public ComentarioDBServiceImpl(ComentarioDBRepository comentarioDBRepository) {
        this.comentarioDBRepository = comentarioDBRepository;
    }

    @Override
    public List<ComentarioDB> getAllComentarios() {
        return comentarioDBRepository.findAll();
    }

    @Override
    public Optional<ComentarioDB> getComentarioById(Long id) {
        return comentarioDBRepository.findById(id);
    }

    @Override
    public ComentarioDB addComentario(ComentarioDB comentarioDB) {
        return comentarioDBRepository.save(comentarioDB);
    }

    @Override
    public ComentarioDB updateComentario(Long id, ComentarioDB comentarioDB) {
        if (comentarioDBRepository.existsById(id)) {
            comentarioDB.setId(id);
            return comentarioDBRepository.save(comentarioDB);
        } else {
            throw new RuntimeException("Comentario no encontrado con el ID: " + id);
        }
    }

    @Override
    public void deleteComentario(Long id) {
        if (comentarioDBRepository.existsById(id)) {
            comentarioDBRepository.deleteById(id);
        } else {
            throw new RuntimeException("Comentario no encontrado con el ID: " + id);
        }
    }

    @Override
    public Optional<ComentarioDB> getComentarioByNombreUsuario(String nombreUsuario) {
        return Optional.ofNullable(comentarioDBRepository.findByNombreUsuario(nombreUsuario));
    }

    // ✅ Corrección: Retornar una lista correctamente
    @Override
    public List<ComentarioDB> getComentariosByNombreUsuario(String nombreUsuario) {
        ComentarioDB comentario = comentarioDBRepository.findByNombreUsuario(nombreUsuario);
        return comentario != null ? Collections.singletonList(comentario) : Collections.emptyList();
    }

    // ✅ Corrección: Implementar correctamente la búsqueda de comentarios por usuario
    @Override
    public List<ComentarioDB> getComentariosByUsuario(String nombreUsuario) {
        return comentarioDBRepository.findByNombreUsuarioContaining(nombreUsuario);
    }

    // ✅ Corrección: Eliminar o modificar este método ya que no tiene sentido con String como parámetro
    @Override
    public Optional<ComentarioDB> getComentarioById(String id) {
        try {
            Long idLong = Long.parseLong(id);
            return comentarioDBRepository.findById(idLong);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}



