package study.yeshm.springboot.grpc.demo.grpc;

import lombok.extern.slf4j.Slf4j;
import study.yeshm.grpc.examples.CalculatorGrpc;
import study.yeshm.grpc.examples.CalculatorOuterClass;
import study.yeshm.springboot.grpc.GRpcService;
import io.grpc.stub.StreamObserver;

/**
 * @author yeshm
 */
@GRpcService(interceptors = GrpcInterceptor.class)
@Slf4j
public class CalculatorGrpcService extends CalculatorGrpc.CalculatorImplBase {

    @Override
    public void calculate(CalculatorOuterClass.CalculatorRequest request, StreamObserver<CalculatorOuterClass.CalculatorResponse> responseObserver) {
        log.info("CalculatorGrpcService_calculate_request:\n{}", request.toString());

        CalculatorOuterClass.CalculatorResponse.Builder resultBuilder = CalculatorOuterClass.CalculatorResponse.newBuilder();
        double result;

        switch (request.getOperation()) {
            case ADD:
                result = request.getNumber1() + request.getNumber2();
                resultBuilder.setResult(result);
                break;
            case SUBTRACT:
                result = request.getNumber1() - request.getNumber2();
                resultBuilder.setResult(result);
                break;
            case MULTIPLY:
                result = request.getNumber1() * request.getNumber2();
                resultBuilder.setResult(result);
                break;
            case DIVIDE:
                result = request.getNumber1() / request.getNumber2();
                resultBuilder.setResult(result);
                break;
            case UNRECOGNIZED:
                break;
            default:
                break;
        }

        CalculatorOuterClass.CalculatorResponse response = resultBuilder.build();

        log.info("CalculatorGrpcService_calculate_response:\n{}", response.toString());

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
