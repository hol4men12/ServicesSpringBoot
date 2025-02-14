package com.example.demo.controllerT;

import com.example.demo.controllers.ComentarioDBController;
import com.example.demo.models.entity.ComentarioDB;
import com.example.demo.models.service.ComentarioDBServiceImpl;
import com.example.demo.repository.ComentarioDBRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ComentarioDBControllerTest {

    @Mock
    private ComentarioDBRepository comentarioDBRepository;

    @Mock
    private ComentarioDBServiceImpl comentarioDBService; // Asegurándonos de que el servicio también está simulado

    @InjectMocks
    private ComentarioDBController comentarioDBController;

    @Test
    void testGetComentarioByNombreUsuario_Encontrado() {
        // Datos de prueba
        String nombreUsuario = "usuarioPrueba";
        ComentarioDB comentarioMock = new ComentarioDB();
        comentarioMock.setNombreUsuario(nombreUsuario);
        comentarioMock.setComentario("Comentario de prueba");
        comentarioMock.setCorreo("correo@ejemplo.com");

        // Simular el comportamiento del repository
        when(comentarioDBRepository.findByNombreUsuario(nombreUsuario))
                .thenReturn(comentarioMock); // Ahora devolvemos el objeto directamente

        // Llamamos al método
        ResponseEntity<ComentarioDB> response = comentarioDBController.getComentarioByNombreUsuario(nombreUsuario);

        // Verificaciones
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(nombreUsuario, response.getBody().getNombreUsuario());
    }

    @Test
    void testGetComentarioByNombreUsuario_NoEncontrado() {
        // Datos de prueba
        String nombreUsuario = "usuarioNoExiste";

        // Simular que el repository no encuentra el usuario
        when(comentarioDBRepository.findByNombreUsuario(nombreUsuario))
                .thenReturn(null);  // Simulamos con null

        // Llamamos al método
        ResponseEntity<ComentarioDB> response = comentarioDBController.getComentarioByNombreUsuario(nombreUsuario);

        // Verificaciones
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }
    
    @Test
    void testPostComentario_Exitoso() {
        // Datos de prueba
        ComentarioDB nuevoComentario = new ComentarioDB();
        nuevoComentario.setNombreUsuario("usuarioPrueba");
        nuevoComentario.setCorreo("usuario@prueba.com");
        nuevoComentario.setComentario("Este es un comentario de prueba.");

        ComentarioDB comentarioExistente = null; // Simulamos que no existe el comentario
        when(comentarioDBRepository.findByNombreUsuario("usuarioPrueba")).thenReturn(null);  // No existe el comentario
        when(comentarioDBService.addComentario(nuevoComentario)).thenReturn(nuevoComentario); // Se guarda correctamente

        // Llamamos al método POST
        ResponseEntity<String> response = comentarioDBController.addComentario(nuevoComentario);

        // Verificaciones
        assertEquals(201, response.getStatusCodeValue()); // Código de estado 201 para éxito de creación
        assertEquals("Comentario agregado correctamente.", response.getBody());
    }

    @Test
    void testPostComentario_Fallo_NombreUsuarioVacio() {
        // Datos de prueba con nombre de usuario vacío
        ComentarioDB comentarioConNombreVacio = new ComentarioDB();
        comentarioConNombreVacio.setNombreUsuario("");
        comentarioConNombreVacio.setCorreo("usuario@prueba.com");
        comentarioConNombreVacio.setComentario("Comentario válido.");

        // Llamamos al método POST
        ResponseEntity<String> response = comentarioDBController.addComentario(comentarioConNombreVacio);

        // Verificaciones
        assertEquals(400, response.getStatusCodeValue()); // Código de estado 400 para error de solicitud
        assertEquals("El campo 'nombreUsuario' es obligatorio.", response.getBody());
    }

    @Test
    void testPostComentario_Fallo_CorreoVacio() {
        // Datos de prueba con correo vacío
        ComentarioDB comentarioConCorreoVacio = new ComentarioDB();
        comentarioConCorreoVacio.setNombreUsuario("usuarioPrueba");
        comentarioConCorreoVacio.setCorreo("");
        comentarioConCorreoVacio.setComentario("Comentario válido.");

        // Llamamos al método POST
        ResponseEntity<String> response = comentarioDBController.addComentario(comentarioConCorreoVacio);

        // Verificaciones
        assertEquals(400, response.getStatusCodeValue()); // Código de estado 400 para error de solicitud
        assertEquals("El campo 'correo' es obligatorio.", response.getBody());
    }

    @Test
    void testPostComentario_Fallo_ComentarioVacio() {
        // Datos de prueba con comentario vacío
        ComentarioDB comentarioConComentarioVacio = new ComentarioDB();
        comentarioConComentarioVacio.setNombreUsuario("usuarioPrueba");
        comentarioConComentarioVacio.setCorreo("usuario@prueba.com");
        comentarioConComentarioVacio.setComentario("");

        // Llamamos al método POST
        ResponseEntity<String> response = comentarioDBController.addComentario(comentarioConComentarioVacio);

        // Verificaciones
        assertEquals(400, response.getStatusCodeValue()); // Código de estado 400 para error de solicitud
        assertEquals("El campo 'comentario' es obligatorio.", response.getBody());
    }

    @Test
    void testPostComentario_Fallo_ComentarioExistente() {
        // Datos de prueba con un nombre de usuario que ya existe
        ComentarioDB comentarioExistente = new ComentarioDB();
        comentarioExistente.setNombreUsuario("usuarioPrueba");
        comentarioExistente.setCorreo("usuario@prueba.com");
        comentarioExistente.setComentario("Este es un comentario de prueba.");

        // Simulamos que ya existe un comentario con este nombre de usuario
        when(comentarioDBRepository.findByNombreUsuario("usuarioPrueba")).thenReturn(comentarioExistente); // Simulamos que existe

        // Llamamos al método POST
        ResponseEntity<String> response = comentarioDBController.addComentario(comentarioExistente);

        // Verificaciones
        assertEquals(400, response.getStatusCodeValue()); // Código de estado 400 para error de solicitud
        assertEquals("Ya existe un comentario con este nombre de usuario.", response.getBody());
    }
    
    @Test
    void testPutComentario_Fallo_ComentarioNoEncontrado() {
        // Datos de prueba con un nombre de usuario que no existe
        ComentarioDB comentarioActualizado = new ComentarioDB();
        comentarioActualizado.setNombreUsuario("usuarioInexistente");
        comentarioActualizado.setCorreo("nuevoCorreo@prueba.com");
        comentarioActualizado.setComentario("Este es un nuevo comentario.");

        // Simulamos que no existe un comentario con este nombre de usuario
        when(comentarioDBRepository.findByNombreUsuario("usuarioInexistente")).thenReturn(null); // Simulamos que no existe

        // Llamamos al método PUT
        ResponseEntity<String> response = comentarioDBController.actualizarComentario("usuarioInexistente", comentarioActualizado);

        // Verificaciones
        assertEquals(404, response.getStatusCodeValue()); // Código de estado 404 para "no encontrado"
        assertEquals("Comentario no encontrado.", response.getBody());
    }

    @SuppressWarnings("deprecation")
	@Test
    void testPutComentario_Exitoso() {
        // Datos de prueba con un nombre de usuario que existe
        ComentarioDB comentarioExistente = new ComentarioDB();
        comentarioExistente.setNombreUsuario("usuarioPrueba");
        comentarioExistente.setCorreo("usuario@prueba.com");
        comentarioExistente.setComentario("Comentario original.");

        // Simulamos que ya existe un comentario con este nombre de usuario
        when(comentarioDBRepository.findByNombreUsuario("usuarioPrueba")).thenReturn(comentarioExistente); // Simulamos que existe

        // Datos del comentario actualizado
        ComentarioDB comentarioActualizado = new ComentarioDB();
        comentarioActualizado.setNombreUsuario("usuarioPrueba");
        comentarioActualizado.setCorreo("nuevoCorreo@prueba.com");
        comentarioActualizado.setComentario("Comentario actualizado.");

        // Simulamos que el método save guarda el comentario actualizado
        when(comentarioDBRepository.save(any(ComentarioDB.class))).thenReturn(comentarioActualizado);

        // Llamamos al método PUT
        ResponseEntity<String> response = comentarioDBController.actualizarComentario("usuarioPrueba", comentarioActualizado);

        // Verificaciones
        assertEquals(200, response.getStatusCodeValue()); // Código de estado 200 para éxito
        assertEquals("Comentario actualizado correctamente.", response.getBody());

        // Verificamos que el método 'save' del repositorio haya sido llamado una vez con el comentario actualizado
        verify(comentarioDBRepository, times(1)).save(comentarioExistente);
    }
    
    @Test
    void testDeleteComentario_Fallo_ComentarioNoEncontrado() {
        // Datos de prueba con un nombre de usuario que no existe
        String nombreUsuarioInexistente = "usuarioInexistente";

        // Simulamos que no existe un comentario con este nombre de usuario
        when(comentarioDBRepository.findByNombreUsuario(nombreUsuarioInexistente)).thenReturn(null); // Simulamos que no existe

        // Llamamos al método DELETE
        ResponseEntity<String> response = comentarioDBController.eliminarComentario(nombreUsuarioInexistente);

        // Verificaciones
        assertEquals(404, response.getStatusCodeValue()); // Código de estado 404 para "no encontrado"
        assertEquals("Comentario no encontrado.", response.getBody());
    }

    @Test
    void testDeleteComentario_Exitoso() {
        // Datos de prueba con un nombre de usuario que existe
        ComentarioDB comentarioExistente = new ComentarioDB();
        comentarioExistente.setNombreUsuario("usuarioPrueba");
        comentarioExistente.setCorreo("usuario@prueba.com");
        comentarioExistente.setComentario("Comentario a eliminar.");

        // Simulamos que ya existe un comentario con este nombre de usuario
        when(comentarioDBRepository.findByNombreUsuario("usuarioPrueba")).thenReturn(comentarioExistente); // Simulamos que existe

        // Llamamos al método DELETE
        ResponseEntity<String> response = comentarioDBController.eliminarComentario("usuarioPrueba");

        // Verificaciones
        assertEquals(200, response.getStatusCodeValue()); // Código de estado 200 para éxito
        assertEquals("Comentario eliminado correctamente.", response.getBody());

        // Verificamos que el método 'delete' del repositorio haya sido llamado una vez con el comentario a eliminar
        verify(comentarioDBRepository, times(1)).delete(comentarioExistente);
    }

}










