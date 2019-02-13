package eu.activageproject.datalake.domain;

import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DataIndex.
 */
@Document(collection = "data_index")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "data_index")
public class DataIndex implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(min = 5, max = 30)
    @Field("index_by")
    private String indexBy;


    private Object parameters;

    // simlife-needle-entity-add-field - Simlife will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIndexBy() {
        return indexBy;
    }

    public DataIndex indexBy(String indexBy) {
        this.indexBy = indexBy;
        return this;
    }

    public Object getParameters() {
        return parameters;
    }

    public void setParameters(Object parameters) {
        this.parameters = parameters;
    }

    public void setIndexBy(String indexBy) {
        this.indexBy = indexBy;
    }
    // simlife-needle-entity-add-getters-setters - Simlife will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DataIndex dataIndex = (DataIndex) o;
        if (dataIndex.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dataIndex.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DataIndex{" +
            "id='" + id + '\'' +
            ", indexBy='" + indexBy + '\'' +
            ", parameters=" + parameters +
            '}';
    }
}
