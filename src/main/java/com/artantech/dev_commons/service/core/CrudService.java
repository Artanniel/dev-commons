package com.artantech.dev_commons.service.core;

import java.util.List;

/**
 * Interface that defines generic CRUD operations.
 * 
 * @param <T> the entity type
 * @param <ID> the entity ID type
 */
public interface CrudService<T, ID> {

    /**
     * Retrieves all entities of type T
     * 
     * @return a list of all entities
     */
    List<T> findAll();

    /**
     * Retrieves an entity by its ID
     * 
     * @param id the entity ID
     * @return the entity
     */
    T findById(ID id);

    /**
     * Saves a new entity
     * 
     * @param entity the entity to save
     * @return the saved entity
     */
    T save(T entity);

    /**
     * Updates an existing entity
     * 
     * @param id the entity ID
     * @param entity the updated entity
     * @return the updated entity
     */
    T update(ID id, T entity);

    /**
     * Deletes an entity by its ID
     * 
     * @param id the entity ID
     */
    void delete(ID id);
}
