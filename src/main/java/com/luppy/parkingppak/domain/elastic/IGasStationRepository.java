package com.luppy.parkingppak.domain.elastic;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface IGasStationRepository extends ElasticsearchRepository<IGasStation, Long> {
}
