package com.luppy.parkingppak.domain;

import com.luppy.parkingppak.init.data.GasStationData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
    private double xCoor;
    private double yCoor;
    private int gasolinePrice;
    private int dieselPrice;

    public void gasStationMapper(GasStationData.Row row) {
        this.uniqueId = row.getUNI_ID();
        this.name = row.getOS_NM();
        this.compName = row.getPOLL_DIV_CD();
        this.xCoor = Double.parseDouble(row.getGIS_X_COOR());
        this.yCoor = Double.parseDouble(row.getGIS_Y_COOR());
    }

    public void updatePrice(boolean isGasoline, int newPrice) {
        System.out.println("newPrice = " + newPrice);
        if (isGasoline) {
            this.gasolinePrice = newPrice;
        } else {
            this.dieselPrice = newPrice;
        }
    }
}
