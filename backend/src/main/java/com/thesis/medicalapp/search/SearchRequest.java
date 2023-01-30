package com.thesis.medicalapp.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SearchRequest implements Serializable {

    private static final long serialVersionUID = 8514625832019794838L;

    private List<FilterRequest> filters;

    private List<SortRequest> sorts;

    private Integer page;

    private Integer size;

    public List<FilterRequest> getFilters() {
        if (Objects.isNull(this.filters)) return new ArrayList<>();
        return this.filters;
    }

    public List<SortRequest> getSorts() {
        if (Objects.isNull(this.sorts)) return new ArrayList<>();
        return this.sorts;
    }

    public SearchRequest(String objectId, Integer page, Integer size) {
        //filter
        this.filters = new ArrayList<>();
        FilterRequest filterRequest = new FilterRequest();
        filterRequest.setKey("objectId");
        filterRequest.setOperator(Operator.EQUAL);
        filterRequest.setFieldType(FieldType.STRING);
        filterRequest.setValue(objectId);
        this.filters.add(filterRequest);
        //sort
        this.sorts = new ArrayList<>();
        SortRequest sortRequest = new SortRequest();
        sortRequest.setKey("date");
        sortRequest.setDirection(SortDirection.DESC);
        this.sorts.add(sortRequest);
        this.page = page;
        this.size = size;
    }

}
