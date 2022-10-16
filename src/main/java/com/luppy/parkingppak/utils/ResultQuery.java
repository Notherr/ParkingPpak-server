package com.luppy.parkingppak.utils;

import java.util.List;

public abstract class ResultQuery {

    private Float timeTook; // Elastic response time
    private Integer numberOfResults; // number of total elements retrieved

    public void setTimeTook(Float timeTook) {
        this.timeTook = timeTook;
    }

    public void setNumberOfResults(Integer numberOfResults) {
        this.numberOfResults = numberOfResults;
    }

    private List<?> data;

    public void setData(List<?> data) {
        this.data = data;
    }

    public Float getTimeTook() {
        return timeTook;
    }

    public Integer getNumberOfResults() {
        return numberOfResults;
    }

    public List<?> getData() {
        return data;
    }
}
