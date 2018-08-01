package study.yeshm.service;

import org.springframework.stereotype.Service;
import study.yeshm.domain.Hotel;
import study.yeshm.mapper.HotelMapper;

import javax.annotation.Resource;

@Service("hotelService")
public class HotelService {

    @Resource
    private HotelMapper hotelMapper;

    public Hotel selectByPrimaryKey(int hotelId) {
        return hotelMapper.selectByPrimaryKey(hotelId);
    }

    public Hotel selectByCityId(int cityId) {
        return hotelMapper.selectByCityId(cityId);
    }

    public Hotel selectByZip(String zip) {
        return hotelMapper.selectByZip(zip);
    }

    public int addHotel(Hotel hotel) {
        return hotelMapper.insert(hotel);
    }
}
