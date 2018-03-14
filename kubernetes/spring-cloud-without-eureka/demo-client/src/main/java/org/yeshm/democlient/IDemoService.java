package org.yeshm.democlient;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "demo-service", url = "http://${SPRING_DEMO_SERVICE_HOST}:9000")
public interface IDemoService {

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    String test();
}
