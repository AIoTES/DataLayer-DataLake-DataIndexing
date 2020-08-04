/** 
* Copyright 2020 National University of Ireland Galway 
* 
* See the NOTICE file distributed with this work for additional information
* regarding copyright ownership.
*
* Licensed under the EUPL, Version 1.2 or – as soon the European Commission 
* approves - subsequent versions of the EUPL (the "Licence"); 
* You may not use this work except in compliance with the Licence. 
* You may obtain a copy of the Licence at: 
*
*     https://joinup.ec.europa.eu/software/page/eupl 
*
* Unless required by applicable law or agreed to in writing, software 
* distributed under the Licence is distributed on an "AS IS" basis, 
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
* See the Licence for the specific language governing permissions and 
* limitations under the Licence. 
*/

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
