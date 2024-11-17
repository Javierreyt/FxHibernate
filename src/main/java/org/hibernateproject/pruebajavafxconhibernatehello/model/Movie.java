package org.hibernateproject.pruebajavafxconhibernatehello.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

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