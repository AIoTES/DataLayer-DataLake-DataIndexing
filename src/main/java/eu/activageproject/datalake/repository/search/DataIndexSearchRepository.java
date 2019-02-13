package eu.activageproject.datalake.repository.search;

import eu.activageproject.datalake.domain.DataIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DataIndex entity.
 */
public interface DataIndexSearchRepository extends ElasticsearchRepository<DataIndex, String> {
}
