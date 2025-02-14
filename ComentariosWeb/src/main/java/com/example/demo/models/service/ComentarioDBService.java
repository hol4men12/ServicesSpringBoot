package com.example.demo.models.service;

import com.example.demo.models.entity.ComentarioDB;
import java.util.List;
import java.util.Optional;

public interface ComentarioDBService {
    
    List<ComentarioDB> getAllComentarios();
    
    Optional<ComentarioDB> getComentarioById(Long id);
    
    ComentarioDB addComentario(ComentarioDB comentarioDB);
    
    ComentarioDB updateComentario(Long id, ComentarioDB comentarioDB);
    
    void deleteComentario(Long id);
    
    Optional<ComentarioDB> getComentarioByNombreUsuario(String nombreUsuario);
    
    List<ComentarioDB> getComentariosByNombreUsuario(String nombreUsuario);
    
    List<ComentarioDB> getComentariosByUsuario(String nombreUsuario);

	Optional<ComentarioDB> getComentarioById(String id);
}






