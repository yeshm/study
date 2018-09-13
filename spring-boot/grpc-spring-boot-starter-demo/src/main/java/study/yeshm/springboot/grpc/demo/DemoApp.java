package study.yeshm.springboot.grpc.demo;


import study.yeshm.grpc.examples.CalculatorGrpc;
import study.yeshm.grpc.examples.CalculatorOuterClass;
import study.yeshm.springboot.grpc.GRpcService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.grpc.stub.StreamObserver;

/**
 * Created by alexf on 28-Jan-16.
 */

@SpringBootApplication
public class DemoApp {

    @Bean
    public GreeterService greeterService() {
		return new GreeterService();
	}

    @GRpcService(interceptors = NotSpringBeanInterceptor.class)
    public static class CalculatorService extends CalculatorGrpc.CalculatorImplBase{
        @Override
        public void calculate(CalculatorOuterClass.CalculatorRequest request, StreamObserver<CalculatorOuterClass.CalculatorResponse> responseObserver) {
            CalculatorOuterClass.CalculatorResponse.Builder resultBuilder = CalculatorOuterClass.CalculatorResponse.newBuilder();
            switch (request.getOperation()){
                case ADD:
                    resultBuilder.setResult(request.getNumber1()+request.getNumber2());
                    break;
                case SUBTRACT:
                    resultBuilder.setResult(request.getNumber1()-request.getNumber2());
                    break;
                case MULTIPLY:
                    resultBuilder.setResult(request.getNumber1()*request.getNumber2());
                    break;
                case DIVIDE:
                    resultBuilder.setResult(request.getNumber1()/request.getNumber2());
                    break;
                case UNRECOGNIZED:
                    break;
            }
            responseObserver.onNext(resultBuilder.build());
            responseObserver.onCompleted();


        }


    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApp.class,args);
    }

}
