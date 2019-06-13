package eu.activageproject.datalake.web.rest;

import eu.activageproject.datalake.DataIntegrationApp;

import eu.activageproject.datalake.domain.DataIndex;
import eu.activageproject.datalake.repository.DataIndexRepository;
import eu.activageproject.datalake.repository.search.DataIndexSearchRepository;
import eu.activageproject.datalake.service.DataIndexService;
import eu.activageproject.datalake.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;


import static eu.activageproject.datalake.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DataIndexResource REST controller.
 *
 * @see DataIndexResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DataIntegrationApp.class)
public class DataIndexResourceIntTest {

    private static final String DEFAULT_INDEX_ID = "AAAAAAAAAA";
    private static final String UPDATED_INDEX_ID = "BBBBBBBBBB";

    private static final String DEFAULT_INDEX_BY = "AAAAAAAAAA";
    private static final String UPDATED_INDEX_BY = "BBBBBBBBBB";

    @Autowired
    private DataIndexRepository dataIndexRepository;



    @Autowired
    private DataIndexService dataIndexService;

    /**
     * This repository is mocked in the eu.activageproject.datalake.repository.search test package.
     *
     * @see eu.activageproject.datalake.repository.search.DataIndexSearchRepositoryMockConfiguration
     */
    @Autowired
    private DataIndexSearchRepository mockDataIndexSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restDataIndexMockMvc;

    private DataIndex dataIndex;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DataIndexResource dataIndexResource = new DataIndexResource(dataIndexService);
        this.restDataIndexMockMvc = MockMvcBuilders.standaloneSetup(dataIndexResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DataIndex createEntity() {
        DataIndex dataIndex = new DataIndex()
            .indexBy(DEFAULT_INDEX_BY);
        return dataIndex;
    }

    @Before
    public void initTest() {
        dataIndexRepository.deleteAll();
        dataIndex = createEntity();
    }

    @Test
    public void createDataIndex() throws Exception {
        int databaseSizeBeforeCreate = dataIndexRepository.findAll().size();

        // Create the DataIndex
        restDataIndexMockMvc.perform(post("/api/data-indices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataIndex)))
            .andExpect(status().isCreated());

        // Validate the DataIndex in the database
        List<DataIndex> dataIndexList = dataIndexRepository.findAll();
        assertThat(dataIndexList).hasSize(databaseSizeBeforeCreate + 1);
        DataIndex testDataIndex = dataIndexList.get(dataIndexList.size() - 1);
        assertThat(testDataIndex.getIndexBy()).isEqualTo(DEFAULT_INDEX_BY);

        // Validate the DataIndex in Elasticsearch
        verify(mockDataIndexSearchRepository, times(1)).save(testDataIndex);
    }

    @Test
    public void createDataIndexWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dataIndexRepository.findAll().size();

        // Create the DataIndex with an existing ID
        dataIndex.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restDataIndexMockMvc.perform(post("/api/data-indices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataIndex)))
            .andExpect(status().isBadRequest());

        // Validate the DataIndex in the database
        List<DataIndex> dataIndexList = dataIndexRepository.findAll();
        assertThat(dataIndexList).hasSize(databaseSizeBeforeCreate);

        // Validate the DataIndex in Elasticsearch
        verify(mockDataIndexSearchRepository, times(0)).save(dataIndex);
    }

    @Test
    public void checkIndexIDIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataIndexRepository.findAll().size();
        // set the field null


        // Create the DataIndex, which fails.

        restDataIndexMockMvc.perform(post("/api/data-indices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataIndex)))
            .andExpect(status().isBadRequest());

        List<DataIndex> dataIndexList = dataIndexRepository.findAll();
        assertThat(dataIndexList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkIndexByIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataIndexRepository.findAll().size();
        // set the field null
        dataIndex.setIndexBy(null);

        // Create the DataIndex, which fails.

        restDataIndexMockMvc.perform(post("/api/data-indices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataIndex)))
            .andExpect(status().isBadRequest());

        List<DataIndex> dataIndexList = dataIndexRepository.findAll();
        assertThat(dataIndexList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllDataIndices() throws Exception {
        // Initialize the database
        dataIndexRepository.save(dataIndex);

        // Get all the dataIndexList
        restDataIndexMockMvc.perform(get("/api/data-indices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataIndex.getId())))
            .andExpect(jsonPath("$.[*].indexID").value(hasItem(DEFAULT_INDEX_ID.toString())))
            .andExpect(jsonPath("$.[*].indexBy").value(hasItem(DEFAULT_INDEX_BY.toString())));
    }


    @Test
    public void getDataIndex() throws Exception {
        // Initialize the database
        dataIndexRepository.save(dataIndex);

        // Get the dataIndex
        restDataIndexMockMvc.perform(get("/api/data-indices/{id}", dataIndex.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dataIndex.getId()))
            .andExpect(jsonPath("$.indexID").value(DEFAULT_INDEX_ID.toString()))
            .andExpect(jsonPath("$.indexBy").value(DEFAULT_INDEX_BY.toString()));
    }
    @Test
    public void getNonExistingDataIndex() throws Exception {
        // Get the dataIndex
        restDataIndexMockMvc.perform(get("/api/data-indices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateDataIndex() throws Exception {
        // Initialize the database
        dataIndexService.save(dataIndex);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockDataIndexSearchRepository);

        int databaseSizeBeforeUpdate = dataIndexRepository.findAll().size();

        // Update the dataIndex
        DataIndex updatedDataIndex = dataIndexRepository.findById(dataIndex.getId()).get();
        updatedDataIndex
            .indexBy(UPDATED_INDEX_BY);

        restDataIndexMockMvc.perform(put("/api/data-indices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDataIndex)))
            .andExpect(status().isOk());

        // Validate the DataIndex in the database
        List<DataIndex> dataIndexList = dataIndexRepository.findAll();
        assertThat(dataIndexList).hasSize(databaseSizeBeforeUpdate);
        DataIndex testDataIndex = dataIndexList.get(dataIndexList.size() - 1);
        assertThat(testDataIndex.getIndexBy()).isEqualTo(UPDATED_INDEX_BY);

        // Validate the DataIndex in Elasticsearch
        verify(mockDataIndexSearchRepository, times(1)).save(testDataIndex);
    }

    @Test
    public void updateNonExistingDataIndex() throws Exception {
        int databaseSizeBeforeUpdate = dataIndexRepository.findAll().size();

        // Create the DataIndex

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDataIndexMockMvc.perform(put("/api/data-indices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dataIndex)))
            .andExpect(status().isBadRequest());

        // Validate the DataIndex in the database
        List<DataIndex> dataIndexList = dataIndexRepository.findAll();
        assertThat(dataIndexList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DataIndex in Elasticsearch
        verify(mockDataIndexSearchRepository, times(0)).save(dataIndex);
    }

    @Test
    public void deleteDataIndex() throws Exception {
        // Initialize the database
        dataIndexService.save(dataIndex);

        int databaseSizeBeforeDelete = dataIndexRepository.findAll().size();

        // Get the dataIndex
        restDataIndexMockMvc.perform(delete("/api/data-indices/{id}", dataIndex.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DataIndex> dataIndexList = dataIndexRepository.findAll();
        assertThat(dataIndexList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DataIndex in Elasticsearch
        verify(mockDataIndexSearchRepository, times(1)).deleteById(dataIndex.getId());
    }

    @Test
    public void searchDataIndex() throws Exception {
        // Initialize the database
        dataIndexService.save(dataIndex);
        when(mockDataIndexSearchRepository.search(queryStringQuery("id:" + dataIndex.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(dataIndex), PageRequest.of(0, 1), 1));
        // Search the dataIndex
        restDataIndexMockMvc.perform(get("/api/_search/data-indices?query=id:" + dataIndex.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataIndex.getId())))
            .andExpect(jsonPath("$.[*].indexID").value(hasItem(DEFAULT_INDEX_ID.toString())))
            .andExpect(jsonPath("$.[*].indexBy").value(hasItem(DEFAULT_INDEX_BY.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DataIndex.class);
        DataIndex dataIndex1 = new DataIndex();
        dataIndex1.setId("id1");
        DataIndex dataIndex2 = new DataIndex();
        dataIndex2.setId(dataIndex1.getId());
        assertThat(dataIndex1).isEqualTo(dataIndex2);
        dataIndex2.setId("id2");
        assertThat(dataIndex1).isNotEqualTo(dataIndex2);
        dataIndex1.setId(null);
        assertThat(dataIndex1).isNotEqualTo(dataIndex2);
    }
}
