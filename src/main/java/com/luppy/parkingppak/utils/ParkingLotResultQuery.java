package com.luppy.parkingppak.utils;

import com.luppy.parkingppak.domain.dto.IParkingLotDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter @Setter
@NoArgsConstructor
public class ParkingLotResultQuery {

    private Float timeTook; // Elastic response time
    private Integer numberOfResults; // number of total elements retrieved
    private List<IParkingLotDto> data;

}
