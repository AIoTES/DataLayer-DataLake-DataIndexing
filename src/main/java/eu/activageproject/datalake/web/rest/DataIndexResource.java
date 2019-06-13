package eu.activageproject.datalake.web.rest;

import com.codahale.metrics.annotation.Timed;
import eu.activageproject.datalake.domain.DataIndex;
import eu.activageproject.datalake.service.DataIndexService;
import eu.activageproject.datalake.web.rest.errors.BadRequestAlertException;
import eu.activageproject.datalake.web.rest.util.HeaderUtil;
import eu.activageproject.datalake.web.rest.util.PaginationUtil;
import eu.activageproject.datalake.web.rest.vm.CreateIndexVM;
import eu.activageproject.datalake.web.rest.vm.UpdateIndexOption;
import io.github.simlife.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.Resource;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing DataIndex.
 */
@RestController
@RequestMapping("/api")
public class DataIndexResource {

    private final Logger log = LoggerFactory.getLogger(DataIndexResource.class);

    private static final String ENTITY_NAME = "dataIndex";

    private final DataIndexService dataIndexService;

    @Value(value = "classpath:apidocs.json")
    private Resource apiDocResource;

    public DataIndexResource(DataIndexService dataIndexService) {
        this.dataIndexService = dataIndexService;
    }

    /**
     * POST  /data-indices : Create a new dataIndex.
     *
     * @param dataIndex the dataIndex to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dataIndex, or with status 400 (Bad Request) if the dataIndex has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/createIndex")
    @Timed
    public ResponseEntity<DataIndex> createDataIndex(@Valid @RequestBody CreateIndexVM dataIndex) throws URISyntaxException {
        log.debug("REST request to save DataIndex : {}", dataIndex);
        DataIndex result = new DataIndex();
        result.setIndexBy(dataIndex.getOptions().getIndexBy());
        result.setParameters(dataIndex.getOptions().getParameters());
        result = dataIndexService.save(result);
        return ResponseEntity.created(new URI("/api/createIndex/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /data-indices : Updates an existing dataIndex.
     *
     * @param dataIndex the dataIndex to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dataIndex,
     * or with status 400 (Bad Request) if the dataIndex is not valid,
     * or with status 500 (Internal Server Error) if the dataIndex couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/updateIndex")
    @Timed
    public ResponseEntity<DataIndex> updateDataIndex(@Valid @RequestBody UpdateIndexOption dataIndex) throws URISyntaxException {
        log.debug("REST request to update DataIndex : {}", dataIndex);
        if (dataIndex != null && dataIndex.getIndexID() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Optional<DataIndex> result = dataIndexService.findOne(dataIndex.getIndexID());
        if(!result.isPresent()) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "id not found !");
        }
        DataIndex updateIndex = result.get();

        updateIndex.setParameters(dataIndex.getParameters());
        updateIndex.setIndexBy(dataIndex.getIndexBy());
        updateIndex = dataIndexService.save(updateIndex);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, updateIndex.getId().toString()))
            .body(updateIndex);
    }

    /**
     * GET  /data-indices : get all the dataIndices.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of dataIndices in body
     */

    @GetMapping("/getAllIndex")
    @Timed
    public ResponseEntity<List<DataIndex>> getAllDataIndices(Pageable pageable) {
        log.debug("REST request to get a page of DataIndices");
        Page<DataIndex> page = dataIndexService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/getAllIndex");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /data-indices/:id : get the "id" dataIndex.
     *
     * @param id the id of the dataIndex to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dataIndex, or with status 404 (Not Found)
     */
    @GetMapping("/getIndex/{id}")
    @Timed
    public ResponseEntity<DataIndex> getDataIndex(@PathVariable String id) {
        log.debug("REST request to get DataIndex : {}", id);
        Optional<DataIndex> dataIndex = dataIndexService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dataIndex);
    }

    /**
     * DELETE  /data-indices/:id : delete the "id" dataIndex.
     *
     * @param id the id of the dataIndex to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/deleteIndex/{id}")
    @Timed
    public ResponseEntity<Void> deleteDataIndex(@PathVariable String id) {
        log.debug("REST request to delete DataIndex : {}", id);
        dataIndexService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/data-indices?query=:query : search for the dataIndex corresponding
     * to the query.
     *
     * @param query the query of the dataIndex search
     * @param pageable the pagination information
     * @return the result of the search
     */
   /* @GetMapping("/_search/data-indices")
    @Timed
    public ResponseEntity<List<DataIndex>> searchDataIndices(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of DataIndices for query {}", query);
        Page<DataIndex> page = dataIndexService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/data-indices");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    } */

    @GetMapping("/docs")
    @Timed
    public Resource getApiDocs() {
        return apiDocResource;
    }

}
