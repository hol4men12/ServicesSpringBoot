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
public class ComentarioControllerVolumeTestActualizar {

    @Autowired
    private ComentarioDBService comentarioDBService; // Inyección del servicio real

    @Test
    void testActualizarComentariosMasivos() {
        int cantidadComentarios = 500; // Número de comentarios a insertar antes de actualizarlos

        // Insertar múltiples comentarios de prueba
        for (int i = 0; i < cantidadComentarios; i++) {
            ComentarioDB comentario = new ComentarioDB();
            comentario.setNombreUsuario("Usuario" + i);
            comentario.setCorreo("usuario" + i + "@test.com");
            comentario.setComentario("Comentario original #" + i);
            comentarioDBService.addComentario(comentario);
        }

        // Obtener todos los comentarios antes de la actualización
        List<ComentarioDB> comentariosAntes = comentarioDBService.getAllComentarios();
        assertFalse(comentariosAntes.isEmpty());
        assertTrue(comentariosAntes.size() >= cantidadComentarios, "No se almacenaron los " + cantidadComentarios + " comentarios esperados antes de la actualización.");

        // Actualizar todos los comentarios
        for (ComentarioDB comentario : comentariosAntes) {
            comentario.setComentario("Comentario actualizado para " + comentario.getNombreUsuario());
            comentarioDBService.updateComentario(comentario.getId(), comentario);
        }

        // Obtener todos los comentarios después de la actualización
        List<ComentarioDB> comentariosDespues = comentarioDBService.getAllComentarios();

        // Validar que los comentarios se actualizaron correctamente
        for (ComentarioDB comentario : comentariosDespues) {
            assertTrue(comentario.getComentario().startsWith("Comentario actualizado"), "El comentario no fue actualizado correctamente.");
        }

        // Mensaje de confirmación en consola (opcional)
        System.out.println("Todos los comentarios fueron actualizados correctamente.");
    }
}

