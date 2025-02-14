package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.models.entity.ComentarioDB;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ComentarioDBRepository extends JpaRepository<ComentarioDB, Long> {
    ComentarioDB findByNombreUsuario(String nombreUsuario);

    List<ComentarioDB> findByNombreUsuarioContaining(String nombreUsuario);
}




