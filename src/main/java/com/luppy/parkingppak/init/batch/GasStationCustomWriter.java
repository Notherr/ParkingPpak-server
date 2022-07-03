package com.luppy.parkingppak.init.batch;

import com.luppy.parkingppak.domain.GasStation;
import com.luppy.parkingppak.domain.GasStationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class GasStationCustomWriter implements ItemWriter<GasStation> {

    @Autowired
    GasStationRepository gasStationRepository;

    @Override
    public void write(List<? extends GasStation> list) throws Exception {
        for (GasStation data : list) {
            log.info("Gas Station CustomWriter :: Writing data :: " + data.getId() + " " + data.getUniqueId() + " " + data.getName());
            gasStationRepository.save(data);
        }
    }
}
