package com.luppy.parkingppak.init.batch;

import com.luppy.parkingppak.domain.GasStation;
import com.luppy.parkingppak.domain.ParkingLot;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class GasStationFieldSetMapper implements FieldSetMapper<GasStation> {


    @Override
    public GasStation mapFieldSet(FieldSet fieldSet) throws BindException {
        Long id = fieldSet.readLong("id");
        String gasStationName = fieldSet.readRawString("name");
        String compName = fieldSet.readRawString("comp_name");
        int dieselPrice = fieldSet.readInt("diesel_price");
        int gasolinePrice = fieldSet.readInt("gasoline_price");
        String uniqueId = fieldSet.readRawString("unique_id");
        double xCoor = fieldSet.readDouble("x_coor");
        double yCoor = fieldSet.readDouble("y_coor");


        return new GasStation(
                id,
                compName,
                gasStationName,
                uniqueId,
                xCoor,
                yCoor,
                gasolinePrice,
                dieselPrice
        );
    }

}
