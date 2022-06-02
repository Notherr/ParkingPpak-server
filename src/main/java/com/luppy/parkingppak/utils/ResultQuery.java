package com.luppy.parkingppak.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
@NoArgsConstructor
public class ResultQuery {

    private Float timeTook; // Elastic response time
    private Integer numberOfResults; // number of total elements retrieved
    private String elements; // JSON total found

}
