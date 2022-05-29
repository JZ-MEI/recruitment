package com.meijinzhi.apply.service.impl;

import com.meijinzhi.apply.dao.CarouselImgMapper;
import com.meijinzhi.apply.entity.CarouselImg;
import com.meijinzhi.apply.service.CarouselImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarouselImgServiceImpl implements CarouselImgService {
    @Autowired
    CarouselImgMapper carouselImgMapper;
    @Override
    public List<CarouselImg> getAllCarousel() {
        return carouselImgMapper.getAllCarouselImg();
    }
}
