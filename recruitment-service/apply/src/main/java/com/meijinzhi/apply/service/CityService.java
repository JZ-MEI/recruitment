package com.meijinzhi.apply.service;

import com.meijinzhi.apply.entity.City;
import com.meijinzhi.apply.entity.Provincial;

import java.util.List;

public interface CityService {
    List<City> getHotCity();
    List<Provincial> getAllProvincial();
    List<City> getAllCity();
}
