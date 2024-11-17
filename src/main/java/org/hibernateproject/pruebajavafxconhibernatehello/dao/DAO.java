package org.hibernateproject.pruebajavafxconhibernatehello.dao;

import java.util.List;

/**
 * Interfaz DAO para definir las operaciones CRUD.
 *
 * @param <T> el tipo de entidad
 */
public interface DAO<T> {
    /**
     * Obtiene todas las entidades.
     *
     * @return una lista de todas las entidades
     */
    public List<T> findAll();

    /**
     * Busca una entidad por su ID.
     *
     * @param id el ID de la entidad
     * @return la entidad encontrada o null si no se encuentra
     */
    public T findById(Long id);

    /**
     * Guarda una nueva entidad.
     *
     * @param t la entidad a guardar
     */
    public void save(T t);

    /**
     * Actualiza una entidad existente.
     *
     * @param t la entidad a actualizar
     */
    public void update(T t);

    /**
     * Elimina una entidad.
     *
     * @param t la entidad a eliminar
     */
    public void delete(T t);
}
