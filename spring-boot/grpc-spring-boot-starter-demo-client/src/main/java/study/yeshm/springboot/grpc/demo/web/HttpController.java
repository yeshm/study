package study.yeshm.springboot.grpc.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author yeshm
 */
@RestController
@RequestMapping(value = {"/http"})
public class HttpController {

    @Autowired
    private RestTemplate restTemplate;

    private String url = "http://localhost:8080/";

    @RequestMapping(value = {"/greet"})
    public String greet() {
        MultiValueMap<String, String> requestEntity = new LinkedMultiValueMap<>();
        requestEntity.add("user", "World");

        String response = restTemplate.postForObject(url + "http/greet", requestEntity, String.class);

        return response;
    }

    @RequestMapping(value = {"/calculate"})
    public String calculate() {
        MultiValueMap<String, String> requestEntity = new LinkedMultiValueMap<>();
        requestEntity.add("number1", "1");
        requestEntity.add("number2", "2");

        String response = restTemplate.postForObject(url + "http/calculate", requestEntity, String.class);

        return response;
    }
}