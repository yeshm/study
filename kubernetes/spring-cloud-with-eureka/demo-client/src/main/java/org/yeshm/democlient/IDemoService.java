package org.yeshm.democlient;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("demo-service")
public interface IDemoService {

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    String test();
}
