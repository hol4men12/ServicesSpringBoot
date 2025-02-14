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
public class ComentarioControllerVolumeTestDelete {

    @Autowired
    private ComentarioDBService comentarioDBService; // Inyección del servicio real

    @Test
    void testEliminarComentariosMasivos() {
        int cantidadComentarios = 1000; // Número de comentarios a insertar antes de eliminarlos

        // Insertar múltiples comentarios de prueba
        for (int i = 0; i < cantidadComentarios; i++) {
            ComentarioDB comentario = new ComentarioDB();
            comentario.setNombreUsuario("Usuario" + i);
            comentario.setCorreo("usuario" + i + "@test.com");
            comentario.setComentario("Comentario de prueba #" + i);
            comentarioDBService.addComentario(comentario);
        }

        // Obtener todos los comentarios antes de la eliminación
        List<ComentarioDB> comentariosAntes = comentarioDBService.getAllComentarios();
        assertFalse(comentariosAntes.isEmpty());
        assertTrue(comentariosAntes.size() >= cantidadComentarios, "No se almacenaron los " + cantidadComentarios + " comentarios esperados antes de la eliminación.");

        // Eliminar todos los comentarios
        for (ComentarioDB comentario : comentariosAntes) {
            comentarioDBService.deleteComentario(comentario.getId());
        }

        // Obtener todos los comentarios después de la eliminación
        List<ComentarioDB> comentariosDespues = comentarioDBService.getAllComentarios();
        
        // Validar que la base de datos quedó vacía
        assertNotNull(comentariosDespues);
        assertTrue(comentariosDespues.isEmpty(), "Los comentarios no fueron eliminados correctamente.");

        // Mensaje de confirmación en consola (opcional)
        System.out.println("Todos los comentarios fueron eliminados correctamente.");
    }
}

