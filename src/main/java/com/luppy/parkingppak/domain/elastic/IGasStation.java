package com.luppy.parkingppak.domain.elastic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;



@Document(indexName = "gas_station")
@Getter
@AllArgsConstructor @NoArgsConstructor
public class IGasStation {

    @Id
    private Long id;

    private String compName;
    private String name;
    private String uniqueId;
    private double lng;
    private double lat;
    private int gasolinePrice;
    private int dieselPrice;
}
