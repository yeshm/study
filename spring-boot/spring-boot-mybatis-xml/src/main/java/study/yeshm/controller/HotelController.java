package study.yeshm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import study.yeshm.domain.Hotel;
import study.yeshm.service.HotelService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/hotel")
public class HotelController {

    @Resource
    private HotelService hotelService;

    @RequestMapping(value = "/selectByPrimaryKey", method = RequestMethod.GET)
    @ResponseBody
    public Hotel selectByPrimaryKey(HttpServletRequest request, Model model) {
        int hotelId = Integer.parseInt(request.getParameter("hotelId"));
        Hotel hotel = this.hotelService.selectByPrimaryKey(hotelId);
        return hotel;
    }

    @RequestMapping(value = "/selectByCityId", method = RequestMethod.GET)
    @ResponseBody
    public Hotel selectByCityId(HttpServletRequest request, Model model) {
        int cityId = Integer.parseInt(request.getParameter("cityId"));
        Hotel hotel = this.hotelService.selectByCityId(cityId);
        return hotel;
    }

    @RequestMapping(value = "/selectByZip", method = RequestMethod.GET)
    @ResponseBody
    public Hotel selectByZip(HttpServletRequest request, Model model) {
        String zip = request.getParameter("zip");
        Hotel hotel = this.hotelService.selectByZip(zip);
        return hotel;
    }

}