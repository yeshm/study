package study.yeshm.springboot.grpc.demo;

import lombok.extern.slf4j.Slf4j;
import study.yeshm.springboot.grpc.GRpcService;

import study.yeshm.grpc.examples.GreeterGrpc;
import study.yeshm.grpc.examples.GreeterOuterClass;
import io.grpc.stub.StreamObserver;
@Slf4j
@GRpcService(interceptors = { LogInterceptor.class })
public class GreeterService extends GreeterGrpc.GreeterImplBase {
    @Override
    public void sayHello(GreeterOuterClass.HelloRequest request, StreamObserver<GreeterOuterClass.HelloReply> responseObserver) {
        String message = "Hello " + request.getName();
        final GreeterOuterClass.HelloReply.Builder replyBuilder = GreeterOuterClass.HelloReply.newBuilder().setMessage(message);
        responseObserver.onNext(replyBuilder.build());
        responseObserver.onCompleted();
        log.info("Returning " +message);
    }
}