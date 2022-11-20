package com.luppy.parkingppak.domain;

import com.luppy.parkingppak.domain.dto.GasStationDto;
import com.luppy.parkingppak.init.data.GasStationData;
import com.luppy.parkingppak.utils.GeoTrans;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GasStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String compName;
    private String name;
    private String uniqueId;
    private String address;
    private double lon;
    private double lat;
    private int gasolinePrice;
    private int dieselPrice;

    private boolean carWash;
    private boolean cvsExist;
    private String tel;

    public void gasStationMapper(GasStationData.Row row) {
        this.uniqueId = row.getUNI_ID();
        this.name = row.getOS_NM();
        this.compName = row.getPOLL_DIV_CO();
        GeoTrans geoTrans = new GeoTrans(Double.parseDouble(row.getGIS_X_COOR()), Double.parseDouble(row.getGIS_Y_COOR()));
        List<Double> geoPts = geoTrans.Katec2Geo();
        this.lon = geoPts.get(0);
        this.lat = geoPts.get(1);
    }

    public void updatePrice(boolean isGasoline, int newPrice) {
        System.out.println("newPrice = " + newPrice);
        if (isGasoline) {
            this.gasolinePrice = newPrice;
        } else {
            this.dieselPrice = newPrice;
        }
    }

    public GasStationDto entityToDto() {
        return GasStationDto.builder()
                .id(id)
                .compName(compName)
                .name(name)
                .uniqueId(uniqueId)
                .address(address)
                .lon(lon)
                .lat(lat)
                .gasolinePrice(gasolinePrice)
                .dieselPrice(dieselPrice)
                .carWash(carWash)
                .cvsExist(cvsExist)
                .tel(tel)
                .isFavorite(Boolean.FALSE)
                .build();
    }
}
