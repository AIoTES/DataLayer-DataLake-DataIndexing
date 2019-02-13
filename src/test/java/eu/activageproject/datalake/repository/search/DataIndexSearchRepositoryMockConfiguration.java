package eu.activageproject.datalake.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of DataIndexSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class DataIndexSearchRepositoryMockConfiguration {

    @MockBean
    private DataIndexSearchRepository mockDataIndexSearchRepository;

}
