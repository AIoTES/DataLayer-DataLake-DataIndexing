package eu.activageproject.datalake.web.rest.vm;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class UpdateIndexOption implements Serializable {
    @NotNull
    @Size(min = 5, max = 30)
    private String indexID;
    @NotNull
    @Size(min = 5, max = 30)
    private String indexBy;

    private Object parameters;


    public Object getParameters() {
        return parameters;
    }

    public void setParameters(Object parameters) {
        this.parameters = parameters;
    }

    public String getIndexID() {
        return indexID;
    }

    public void setIndexID(String indexID) {
        this.indexID = indexID;
    }

    public String getIndexBy() {
        return indexBy;
    }

    public void setIndexBy(String indexBy) {
        this.indexBy = indexBy;
    }

    @Override
    public String toString() {
        return "CreateIndexOption{" +
            "indexID='" + indexID + '\'' +
            ", indexBy='" + indexBy + '\'' +
            ", parameters=" + parameters +
            '}';
    }
}
