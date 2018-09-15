package study.yeshm.springboot.grpc.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import study.yeshm.springboot.grpc.demo.grpc.CalculatorGrpcClient;
import study.yeshm.springboot.grpc.demo.grpc.GreeterGrpcClient;

@RestController
@RequestMapping(value = {"/http"})
public class HttpController {

    @Autowired
    private RestTemplate restTemplate;

    private String url = "http://localhost:8080/";

    @RequestMapping(value = {"/greet"})
    public String greet() throws InterruptedException {
        String response = restTemplate.getForEntity(url+"http/greet", String.class).getBody();

        return response;
    }

    @RequestMapping(value = {"/calculate"})
    public String calculate() throws InterruptedException {
        String response = restTemplate.getForEntity(url+"http/calculate", String.class).getBody();

        return response;
    }
}