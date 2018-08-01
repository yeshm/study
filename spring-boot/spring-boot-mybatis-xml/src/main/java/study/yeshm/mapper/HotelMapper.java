package study.yeshm.mapper;

import study.yeshm.domain.Hotel;

public interface HotelMapper {
    int deleteByPrimaryKey(Integer hotelId);

    int insert(Hotel record);

    int insertSelective(Hotel record);

    Hotel selectByPrimaryKey(Integer hotelId);

    Hotel selectByCityId(int cityId);

    Hotel selectByZip(String zip);

    int updateByPrimaryKeySelective(Hotel record);

    int updateByPrimaryKey(Hotel record);
}