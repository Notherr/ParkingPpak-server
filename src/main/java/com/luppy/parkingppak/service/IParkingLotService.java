package com.luppy.parkingppak.service;

import com.luppy.parkingppak.domain.elastic.IParkingLotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IParkingLotService {

    private final IParkingLotRepository iParkingLotRepository;

}
