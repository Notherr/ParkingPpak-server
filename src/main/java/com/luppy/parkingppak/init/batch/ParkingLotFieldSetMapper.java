package com.luppy.parkingppak.init.batch;

import com.luppy.parkingppak.domain.ParkingLot;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ParkingLotFieldSetMapper implements FieldSetMapper<ParkingLot> {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @Override
    public ParkingLot mapFieldSet(FieldSet fieldSet) throws BindException {
        Long id = fieldSet.readLong("id");
        String parkingName = fieldSet.readRawString("parking_name");
        String address = fieldSet.readRawString("address");
        int parkingCode = fieldSet.readInt("parking_code");
        String type = fieldSet.readRawString("type");
        String phoneNumber;
        try {
            phoneNumber = fieldSet.readRawString("phone_number");
        } catch (NullPointerException exception) {
            phoneNumber = "";
        }
        Boolean payYN;
        try {
            payYN = fieldSet.readString("payyn") == "t" ? true : false;
        } catch (NullPointerException exception) {
            payYN = false;
        }
        String weekdayBegin = fieldSet.readRawString("weekday_begin");
        String weekdayEnd = fieldSet.readRawString("weekday_end");
        String weekendBegin = fieldSet.readRawString("weekend_begin");
        String weekendEnd = fieldSet.readRawString("weekend_end");
        String holidayBegin = fieldSet.readRawString("holiday_begin");
        String holidayEnd = fieldSet.readRawString("holiday_end");
        LocalDateTime syncTime = LocalDateTime.parse(fieldSet.readRawString("sync_time"), formatter);
        int rates = fieldSet.readInt("rates");
        int timeRates = fieldSet.readInt("time_rates");
        int addRates = fieldSet.readInt("add_rates");
        int addTimeRates = fieldSet.readInt("add_time_rates");
        double lon = fieldSet.readDouble("lon");
        double lat = fieldSet.readDouble("lat");
        LocalDateTime modificationDate = LocalDateTime.parse(fieldSet.readRawString("modification_date"), formatter);

        return new ParkingLot(id,
                modificationDate,
                parkingName,
                address,
                parkingCode,
                type,
                phoneNumber,
                payYN,
                weekdayBegin,
                weekdayEnd,
                weekendBegin,
                weekendEnd,
                holidayBegin,
                holidayEnd,
                syncTime,
                rates,
                timeRates,
                addRates,
                addTimeRates,
                lat,
                lon);
    }

}
