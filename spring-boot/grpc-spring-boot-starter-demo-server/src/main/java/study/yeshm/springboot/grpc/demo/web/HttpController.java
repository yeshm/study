package study.yeshm.springboot.grpc.demo.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yeshm
 */
@RestController
@Slf4j
@RequestMapping(value = {"/http"})
public class HttpController {

    @RequestMapping(value = {"/greet"})
    public String greet() {
        String response = "Hello World";
        log.info("Returning " + response);

        return response;
    }

    @RequestMapping(value = {"/calculate"})
    public double calculate() throws InterruptedException {

        double number1 = 1;
        double number2 = 2;

        double result = number1 + number2;

        return result;
    }
}