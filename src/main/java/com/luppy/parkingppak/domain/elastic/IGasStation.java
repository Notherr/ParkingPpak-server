package com.luppy.parkingppak.domain.elastic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.GeoPointField;


@Document(indexName = "gas_station")
@Getter
@AllArgsConstructor @NoArgsConstructor
public class IGasStation {

    @Id
    private Long id;

    private String compName;
    private String name;
    private String uniqueId;
    private String address;
    @GeoPointField
    private GeoPoint location;
    private int gasolinePrice;
    private int dieselPrice;
    private boolean carWash;
    private boolean cvsExist;
    private String tel;
}
