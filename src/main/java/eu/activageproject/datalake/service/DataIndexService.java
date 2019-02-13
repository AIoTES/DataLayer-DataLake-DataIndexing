package eu.activageproject.datalake.service;

import eu.activageproject.datalake.domain.DataIndex;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing DataIndex.
 */
public interface DataIndexService {

    /**
     * Save a dataIndex.
     *
     * @param dataIndex the entity to save
     * @return the persisted entity
     */
    DataIndex save(DataIndex dataIndex);

    /**
     * Get all the dataIndices.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DataIndex> findAll(Pageable pageable);


    /**
     * Get the "id" dataIndex.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<DataIndex> findOne(String id);

    /**
     * Delete the "id" dataIndex.
     *
     * @param id the id of the entity
     */
    void delete(String id);

    /**
     * Search for the dataIndex corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DataIndex> search(String query, Pageable pageable);
}
