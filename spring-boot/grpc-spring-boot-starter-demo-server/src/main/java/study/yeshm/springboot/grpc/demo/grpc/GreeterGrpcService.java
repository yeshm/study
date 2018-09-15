package study.yeshm.springboot.grpc.demo.grpc;

import lombok.extern.slf4j.Slf4j;
import study.yeshm.springboot.grpc.GRpcService;

import study.yeshm.grpc.examples.GreeterGrpc;
import study.yeshm.grpc.examples.GreeterOuterClass;
import io.grpc.stub.StreamObserver;

/**
 * @author yeshm
 */
@Slf4j
@GRpcService(interceptors = {GrpcInterceptor.class})
public class GreeterGrpcService extends GreeterGrpc.GreeterImplBase {

    public GreeterGrpcService() {
        log.info("new GreeterGrpcService...");
    }

    @Override
    public void sayHello(GreeterOuterClass.HelloRequest request, StreamObserver<GreeterOuterClass.HelloReply> responseObserver) {
        log.info("GreeterGrpcService_sayHello_request:\n{}", request.toString());

        String message = "Hello " + request.getName();

        final GreeterOuterClass.HelloReply.Builder replyBuilder = GreeterOuterClass.HelloReply.newBuilder().setMessage(message);
        GreeterOuterClass.HelloReply response = replyBuilder.build();

        log.info("GreeterGrpcService_sayHello_response:\n{}", response.toString());

        responseObserver.onNext(replyBuilder.build());
        responseObserver.onCompleted();
    }
}