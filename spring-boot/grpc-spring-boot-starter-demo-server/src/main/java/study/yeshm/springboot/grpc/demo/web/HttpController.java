package study.yeshm.springboot.grpc.demo.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yeshm
 */
@RestController
@Slf4j
@RequestMapping(value = {"/http"})
public class HttpController {

    @RequestMapping(value = {"/greet"})
    public String greet(@RequestParam String user) {
        log.info("HttpController_greet_request, user:{}", user);

        String response = "Hello " + user;

        log.info("HttpController_greet_response, {}", response);

        return response;
    }

    @RequestMapping(value = {"/calculate"})
    public double calculate(@RequestParam double number1, @RequestParam double number2) throws InterruptedException {
        log.info("HttpController_calculate_request, number1:{} number2:{}", number1, number2);

        double result = number1 + number2;

        log.info("HttpController_calculate_response, {}", result);

        return result;
    }
}
