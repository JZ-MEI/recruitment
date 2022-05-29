package com.meijinzhi.apply.dao;

import com.meijinzhi.apply.entity.CarouselImg;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CarouselImgMapper {
    List<CarouselImg> getAllCarouselImg();
}
