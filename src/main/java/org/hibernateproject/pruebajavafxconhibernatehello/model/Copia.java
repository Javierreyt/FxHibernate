package org.hibernateproject.pruebajavafxconhibernatehello.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Copia", schema = "bdFXHibernate")
public class Copia implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_pelicula", nullable = false)
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private User user;

    @Column(name = "estado", nullable = false, length = 50)
    private String estado;

    @Column(name = "soporte", nullable = false, length = 50)
    private String soporte;

    public Copia(Movie movie, User user, String estado, String soporte) {
        this.movie = movie;
        this.user = user;
        this.estado = estado;
        this.soporte = soporte;
    }
}