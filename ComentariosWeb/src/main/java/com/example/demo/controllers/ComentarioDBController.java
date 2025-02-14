package com.example.demo.controllers;

import com.example.demo.models.entity.ComentarioDB;
import com.example.demo.models.service.ComentarioDBService;
import com.example.demo.repository.ComentarioDBRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comentarios")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
public class ComentarioDBController {

    private final ComentarioDBService comentarioDBService;
    private final ComentarioDBRepository comentarioDBRepository;

    @Autowired
    public ComentarioDBController(ComentarioDBService comentarioDBService, ComentarioDBRepository comentarioDBRepository) {
        this.comentarioDBService = comentarioDBService;
        this.comentarioDBRepository = comentarioDBRepository;
    }

    @GetMapping
    public List<ComentarioDB> getAllComentarios() {
        return comentarioDBService.getAllComentarios();
    }

    @GetMapping("/{nombreUsuario}")
    public ResponseEntity<ComentarioDB> getComentarioByNombreUsuario(@PathVariable String nombreUsuario) {
        Optional<ComentarioDB> comentario = Optional.ofNullable(comentarioDBRepository.findByNombreUsuario(nombreUsuario));
        return comentario.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PostMapping
    public ResponseEntity<String> addComentario(@RequestBody ComentarioDB comentarioDB) {
        System.out.println("📢 Recibido en el backend: " + comentarioDB);

        // Validaciones de los campos
        if (comentarioDB.getNombreUsuario() == null || comentarioDB.getNombreUsuario().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El campo 'nombreUsuario' es obligatorio.");
        }
        if (comentarioDB.getCorreo() == null || comentarioDB.getCorreo().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El campo 'correo' es obligatorio.");
        }
        if (comentarioDB.getComentario() == null || comentarioDB.getComentario().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El campo 'comentario' es obligatorio.");
        }

        // Buscar si ya existe un comentario con el mismo nombre de usuario
        ComentarioDB comentarioExistente = comentarioDBRepository.findByNombreUsuario(comentarioDB.getNombreUsuario());
        if (comentarioExistente != null) {
            return ResponseEntity.badRequest().body("Ya existe un comentario con este nombre de usuario.");
        }

        comentarioDB.setId(null); // Asignar un ID nulo para la creación
        comentarioDBService.addComentario(comentarioDB); // Guardar el comentario
        return ResponseEntity.status(HttpStatus.CREATED).body("Comentario agregado correctamente.");
    }


    @PutMapping("/{nombreUsuario}")
    public ResponseEntity<String> actualizarComentario(@PathVariable String nombreUsuario, @RequestBody ComentarioDB comentarioActualizado) {
        Optional<ComentarioDB> comentarioExistente = Optional.ofNullable(comentarioDBRepository.findByNombreUsuario(nombreUsuario));

        if (comentarioExistente.isPresent()) {
            ComentarioDB comentario = comentarioExistente.get();
            comentario.setComentario(comentarioActualizado.getComentario());
            comentario.setCorreo(comentarioActualizado.getCorreo());
            comentarioDBRepository.save(comentario);
            return ResponseEntity.ok("Comentario actualizado correctamente.");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comentario no encontrado.");
    }

    @DeleteMapping("/{nombreUsuario}")
    public ResponseEntity<String> eliminarComentario(@PathVariable String nombreUsuario) {
        Optional<ComentarioDB> comentario = Optional.ofNullable(comentarioDBRepository.findByNombreUsuario(nombreUsuario));

        if (comentario.isPresent()) {
            comentarioDBRepository.delete(comentario.get());
            return ResponseEntity.ok("Comentario eliminado correctamente.");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comentario no encontrado.");
    }
    
    
}





