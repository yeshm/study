package study.yeshm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import study.yeshm.domain.City;
import study.yeshm.service.CityService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/city")
public class CityController {

    @Resource
    private CityService cityService;

    @RequestMapping(value = "/selectByPrimaryKey", method = RequestMethod.GET)
    @ResponseBody
    public City getCity(HttpServletRequest request, Model model) {
        int cityId = Integer.parseInt(request.getParameter("cityId"));
        City city = this.cityService.selectByPrimaryKey(cityId);
        return city;
    }

}