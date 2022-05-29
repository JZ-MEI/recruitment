package com.meijinzhi.apply.service.impl;

import com.meijinzhi.apply.dao.CityMapper;
import com.meijinzhi.apply.entity.City;
import com.meijinzhi.apply.entity.Provincial;
import com.meijinzhi.apply.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {
    @Autowired
    CityMapper cityMapper;

    @Override
    public List<City> getHotCity() {
        return cityMapper.getHotCity();
    }

    @Override
    public List<Provincial> getAllProvincial() {
        return cityMapper.getAllProvincial();
    }

    @Override
    public List<City> getAllCity() {
        return cityMapper.getAllCity();
    }
}
