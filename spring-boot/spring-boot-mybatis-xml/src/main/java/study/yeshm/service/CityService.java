package study.yeshm.service;

import org.springframework.stereotype.Service;
import study.yeshm.domain.City;
import study.yeshm.mapper.CityMapper;

import javax.annotation.Resource;

@Service("cityService")
public class CityService {

    @Resource
    private CityMapper cityMapper;


    public City selectByPrimaryKey(int cityId) {
        return cityMapper.selectByPrimaryKey(cityId);
    }

    public int addCity(City city) {
        return cityMapper.insert(city);
    }
}
