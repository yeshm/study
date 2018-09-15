package study.yeshm.springboot.grpc.demo.grpc;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yeshm
 */
@Slf4j
public class GrpcInterceptor implements ServerInterceptor {

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> call,
            Metadata headers,
            ServerCallHandler<ReqT, RespT> next) {

        log.info("GrpcInterceptor begin...");
        log.info("grpc method:{}", call.getMethodDescriptor().getFullMethodName());
        log.info("grpc headers key:");

        for (String key : headers.keys()) {
            log.info("  " + key);
        }

        log.info("GrpcInterceptor end...");

        return next.startCall(call, headers);
    }
}
