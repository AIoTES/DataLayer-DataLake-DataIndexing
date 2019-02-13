package eu.activageproject.datalake.repository;

import eu.activageproject.datalake.domain.DataIndex;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the DataIndex entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DataIndexRepository extends MongoRepository<DataIndex, String> {

}
