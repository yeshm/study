package org.yeshm.demoservice;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoServiceController {
    private final Logger logger = Logger.getLogger(getClass());

    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        StringBuffer sb = new StringBuffer();

        sb.append("当前时间:" + df.format(new Date()));

        sb.append(", service_host:" + System.getenv("HOSTNAME"));

        logger.info(sb.toString());

        return sb.toString();
    }
}
