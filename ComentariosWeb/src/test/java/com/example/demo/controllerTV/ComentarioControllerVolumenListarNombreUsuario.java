package com.example.demo.controllerTV;

import com.example.demo.models.entity.ComentarioDB;
import com.example.demo.models.service.ComentarioDBService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ComentarioControllerVolumenListarNombreUsuario {

    @Autowired
    private ComentarioDBService comentarioDBService; // Inyectamos el servicio real

    @Test
    void testListarComentariosPorNombreUsuario() {
        String nombreUsuario = "UsuarioTest";

        // Insertar múltiples comentarios de prueba para el usuario
        for (int i = 0; i < 500; i++) {
            ComentarioDB comentario = new ComentarioDB();
            comentario.setNombreUsuario(nombreUsuario);
            comentario.setCorreo("usuariotest@test.com");
            comentario.setComentario("Comentario de prueba #" + i);
            comentarioDBService.addComentario(comentario);
        }

        // Obtener los comentarios desde la base de datos
        List<ComentarioDB> comentarios = comentarioDBService.getComentariosByUsuario(nombreUsuario);

        // Validar que se hayan guardado correctamente
        assertNotNull(comentarios);
        assertFalse(comentarios.isEmpty());
        assertEquals(500, comentarios.size(), "No se almacenaron los 500 comentarios esperados.");

        // Imprimir algunos comentarios para verificar (opcional)
        System.out.println("Primer comentario: " + comentarios.get(0).getComentario());
        System.out.println("Último comentario: " + comentarios.get(comentarios.size() - 1).getComentario());
    }
}

