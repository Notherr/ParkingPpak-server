package com.luppy.parkingppak.init.batch;

import com.luppy.parkingppak.domain.GasStation;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;


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
        double lon = fieldSet.readDouble("lon");
        double lat = fieldSet.readDouble("lat");
        String address = fieldSet.readRawString("address");
        String str_carWash = fieldSet.readRawString("car_wash");
        String str_cvsExist = fieldSet.readRawString("cvs_exist");
        System.out.println("str_cvsExist = " + str_cvsExist);
        String tel = fieldSet.readRawString("tel");
        boolean carWash =  (str_carWash.equals("t")) ? true : false;
        boolean cvsExist = (str_cvsExist.equals("t")) ? true : false;

        return GasStation.builder()
                .id(id)
                .compName(compName)
                .name(gasStationName)
                .uniqueId(uniqueId)
                .lon(lon)
                .lat(lat)
                .gasolinePrice(gasolinePrice)
                .dieselPrice(dieselPrice)
                .address(address)
                .carWash(carWash)
                .cvsExist(cvsExist)
                .tel(tel)
                .build();

    }

}
