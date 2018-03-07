package org.yeshm.democlient;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoClientController {
    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private DiscoveryClient client;

    @Autowired
    private IDemoService iDemoService;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        ServiceInstance instance = client.getLocalServiceInstance();
        String result = iDemoService.test();

        StringBuffer sb = new StringBuffer();

        sb.append(result);
        sb.append(" | client_host:" + instance.getHost());
        sb.append(", client_id:" + instance.getServiceId());

        logger.info(sb.toString());

        return sb.toString();
    }
}
