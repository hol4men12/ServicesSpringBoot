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
public class ComentarioControllerVolumeListarTodosTest {

    @Autowired
    private ComentarioDBService comentarioDBService; // Inyección del servicio real

    @Test
    void testListarTodosLosComentarios() {
        int cantidadComentarios = 1000; // Número de comentarios a insertar

        // Insertar múltiples comentarios de prueba
        for (int i = 0; i < cantidadComentarios; i++) {
            ComentarioDB comentario = new ComentarioDB();
            comentario.setNombreUsuario("Usuario" + i);
            comentario.setCorreo("usuario" + i + "@test.com");
            comentario.setComentario("Comentario de prueba #" + i);
            comentarioDBService.addComentario(comentario);
        }

        // Obtener todos los comentarios desde la base de datos
        List<ComentarioDB> comentarios = comentarioDBService.getAllComentarios();

        // Validar que la cantidad de comentarios en la base de datos sea al menos la que se insertó
        assertNotNull(comentarios);
        assertFalse(comentarios.isEmpty());
        assertTrue(comentarios.size() >= cantidadComentarios, "No se almacenaron los " + cantidadComentarios + " comentarios esperados.");

        // Imprimir algunos comentarios para verificar (opcional)
        System.out.println("Total comentarios en BD: " + comentarios.size());
        System.out.println("Primer comentario: " + comentarios.get(0).getComentario());
        System.out.println("Último comentario: " + comentarios.get(comentarios.size() - 1).getComentario());
    }
}

