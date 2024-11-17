package org.hibernateproject.pruebajavafxconhibernatehello.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "User", schema = "bdFXHibernate")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "nombre_usuario", nullable = false, length = 50)
    private String nombreUsuario;

    @Column(name = "`contraseña`", nullable = false)
    private String contraseña;

    @Column(name = "is_admin", nullable = false)
    private Boolean isAdmin;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Copia> copias;
}