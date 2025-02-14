package com.example.demo.models.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "comentarios")
public class ComentarioDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "correo") // ✅ Define explícitamente el orden
    private String correo;

    @Column(name = "nombre_usuario") // ✅ Define explícitamente el orden
    private String nombreUsuario;

    @Column(name = "comentario") // ✅ Define explícitamente el orden
    private String comentario;

    public ComentarioDB() {}

    public ComentarioDB(String correo, String nombreUsuario, String comentario) {
        this.correo = correo;
        this.nombreUsuario = nombreUsuario;
        this.comentario = comentario;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }

	public boolean isPresent() {
		// TODO Auto-generated method stub
		return false;
	}
}





