package org.hibernateproject.pruebajavafxconhibernatehello.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * Representa una película en la base de datos.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "Movie", schema = "bdFXHibernate")
public class Movie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "titulo", nullable = false, length = 100)
    private String titulo;

    @Column(name = "genero", nullable = false, length = 50)
    private String genero;

    @Column(name = "`año`", nullable = false)
    private Integer año;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "director", nullable = false, length = 100)
    private String director;

    @Column(name = "cover", nullable = false, length = 100)
    private String imagen;

    @Column(name = "url", nullable = false, length = 100)
    private String url;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Copia> copias;

    /**
     * Constructor para crear una nueva película.
     *
     * @param titulo      el título de la película
     * @param genero      el género de la película
     * @param año         el año de lanzamiento de la película
     * @param descripcion una breve descripción de la película
     * @param director    el director de la película
     * @param imagen      la URL de la imagen de portada de la película
     * @param url         la URL de la película
     */
    public Movie(String titulo, String genero, Integer año, String descripcion, String director, String imagen, String url) {
        this.titulo = titulo;
        this.genero = genero;
        this.año = año;
        this.descripcion = descripcion;
        this.director = director;
        this.imagen = imagen;
        this.url = url;
    }
}