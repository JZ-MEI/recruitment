package com.meijinzhi.apply.dao;

import com.meijinzhi.apply.entity.City;
import com.meijinzhi.apply.entity.Provincial;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CityMapper {
    List<City> getHotCity();
    List<Provincial> getAllProvincial();
    List<City> getAllCity();
}
