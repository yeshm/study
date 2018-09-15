package study.yeshm.springboot.grpc.demo.grpc;

import io.grpc.*;

/**
 * @author yeshm
 */
public class GrpcHelper {
    private static volatile ManagedChannel instance;

    public static ManagedChannel getChannel(String host, int port) {
        if (instance == null) {
            synchronized (GrpcHelper.class) {
                if (instance == null) {
                    instance = ManagedChannelBuilder.forAddress(host, port)
                            // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                            // needing certificates.
                            .usePlaintext()
                            .build();
                }
            }
        }

        return instance;
    }
}
