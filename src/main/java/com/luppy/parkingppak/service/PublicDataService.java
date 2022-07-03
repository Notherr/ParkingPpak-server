package com.luppy.parkingppak.service;

import java.io.IOException;

public interface PublicDataService<E>{

    void registerData(E e);

    E searchData(Long id);

    E updateData(Long id, E e);

    void processRegister() throws IOException, InterruptedException;
}
