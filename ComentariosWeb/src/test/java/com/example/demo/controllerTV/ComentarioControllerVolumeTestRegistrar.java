package com.example.demo.controllerTV;

import com.example.demo.models.entity.ComentarioDB;
import com.example.demo.models.service.ComentarioDBService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ComentarioControllerVolumeTestRegistrar {

    @Autowired
    private ComentarioDBService comentarioDBService; // Usamos la base de datos real

    @Test
    void testRegistrarComentariosExcesivos() {
        // Crear una lista de 1000 comentarios
        List<ComentarioDB> comentariosMasivos = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            ComentarioDB comentario = new ComentarioDB();
            comentario.setNombreUsuario("Usuario" + i);
            comentario.setCorreo("usuario" + i + "@test.com");
            comentario.setComentario("Comentario de prueba número " + i);
            comentariosMasivos.add(comentario);
        }

        // Guardamos los comentarios en la base de datos
        for (ComentarioDB comentario : comentariosMasivos) {
            ComentarioDB resultado = comentarioDBService.addComentario(comentario);
            assertNotNull(resultado);
            assertEquals(comentario.getNombreUsuario(), resultado.getNombreUsuario());
        }

        // Verificamos que los comentarios realmente estén en la base de datos
        assertTrue(comentarioDBService.getComentariosByUsuario("Usuario500").size() > 0);
    }
}



