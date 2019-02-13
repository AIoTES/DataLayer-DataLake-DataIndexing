package eu.activageproject.datalake.service.impl;

import eu.activageproject.datalake.service.DataIndexService;
import eu.activageproject.datalake.domain.DataIndex;
import eu.activageproject.datalake.repository.DataIndexRepository;
import eu.activageproject.datalake.repository.search.DataIndexSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing DataIndex.
 */
@Service
public class DataIndexServiceImpl implements DataIndexService {

    private final Logger log = LoggerFactory.getLogger(DataIndexServiceImpl.class);

    private final DataIndexRepository dataIndexRepository;

    private final DataIndexSearchRepository dataIndexSearchRepository;

    public DataIndexServiceImpl(DataIndexRepository dataIndexRepository, DataIndexSearchRepository dataIndexSearchRepository) {
        this.dataIndexRepository = dataIndexRepository;
        this.dataIndexSearchRepository = dataIndexSearchRepository;
    }

    /**
     * Save a dataIndex.
     *
     * @param dataIndex the entity to save
     * @return the persisted entity
     */
    @Override
    public DataIndex save(DataIndex dataIndex) {
        log.debug("Request to save DataIndex : {}", dataIndex);
        DataIndex result = dataIndexRepository.save(dataIndex);
        dataIndexSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the dataIndices.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<DataIndex> findAll(Pageable pageable) {
        log.debug("Request to get all DataIndices");
        return dataIndexRepository.findAll(pageable);
    }


    /**
     * Get one dataIndex by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public Optional<DataIndex> findOne(String id) {
        log.debug("Request to get DataIndex : {}", id);
        return dataIndexRepository.findById(id);
    }

    /**
     * Delete the dataIndex by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete DataIndex : {}", id);
        dataIndexRepository.deleteById(id);
        dataIndexSearchRepository.deleteById(id);
    }

    /**
     * Search for the dataIndex corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<DataIndex> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DataIndices for query {}", query);
        return dataIndexSearchRepository.search(queryStringQuery(query), pageable);    }
}
