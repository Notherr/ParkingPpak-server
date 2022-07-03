package com.luppy.parkingppak.service;

public interface PublicDataService<E>{

    void registerData(E e);

    E searchData(Long id);

    E updateData(Long id, E e);
}
