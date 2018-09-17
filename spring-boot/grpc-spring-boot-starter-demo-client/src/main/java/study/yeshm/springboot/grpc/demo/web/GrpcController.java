package study.yeshm.springboot.grpc.demo.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.yeshm.springboot.grpc.demo.grpc.CalculatorGrpcClient;
import study.yeshm.springboot.grpc.demo.grpc.GreeterGrpcClient;

@RestController
@RequestMapping(value = {"/grpc"})
public class GrpcController {

    @RequestMapping(value = {"/greet"})
    public String greet() throws InterruptedException {
        GreeterGrpcClient client = GreeterGrpcClient.getInstance("grpc-hello-world-server", 50051);
        String response;

        try {
            /* Access a service running on the local machine on port 50051 */
            String user = "World";
            response = client.greet(user);

        } finally {
//            System.out.printf("shutting down client...");
//            client.shutdown();
        }

        return response;
    }

    @RequestMapping(value = {"/calculate"})
    public double calculate() throws InterruptedException {
        CalculatorGrpcClient client = CalculatorGrpcClient.getInstance("grpc-hello-world-server", 50051);
        double response;

        try {
            /* Access a service running on the local machine on port 50051 */
            response = client.calculate();

        } finally {
//            System.out.printf("shutting down client...");
//            client.shutdown();
        }

        return response;
    }
}