package study.yeshm.springboot.grpc.demo.grpc;

import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import study.yeshm.grpc.examples.CalculatorGrpc;
import study.yeshm.grpc.examples.CalculatorOuterClass;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author yeshm
 */
public class CalculatorGrpcClient {

    private static final Logger logger = Logger.getLogger(CalculatorGrpcClient.class.getName());

    private final ManagedChannel channel;
    private final CalculatorGrpc.CalculatorBlockingStub blockingStub;

    private static volatile CalculatorGrpcClient instance;

    public static CalculatorGrpcClient getInstance(String host, int port) {
        if (instance == null) {
            synchronized (CalculatorGrpcClient.class) {
                if (instance == null) {
                    instance = new CalculatorGrpcClient(host, port);
                }
            }
        }

        return instance;
    }

    /**
     * Construct client connecting to HelloWorld server at {@code host:port}.
     */
    public CalculatorGrpcClient(String host, int port) {
        this(GrpcHelper.getChannel(host, port));
    }

    /**
     * Construct client for accessing HelloWorld server using the existing channel.
     */
    CalculatorGrpcClient(ManagedChannel channel) {
        this.channel = channel;
        blockingStub = CalculatorGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    /**
     * Say hello to server.
     */
    public double calculate() {
        logger.info("Will try to calculate... ");
        CalculatorOuterClass.CalculatorRequest request = CalculatorOuterClass.CalculatorRequest.newBuilder()
                .setNumber1(1)
                .setNumber2(2)
                .setOperation(CalculatorOuterClass.CalculatorRequest.OperationType.ADD)
                .build();
        CalculatorOuterClass.CalculatorResponse response;
        try {
            response = blockingStub.calculate(request);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return -1;
        }
        logger.info("calculate result: " + response.getResult());
        return response.getResult();
    }

    public static void main(String[] args) throws Exception {
        CalculatorGrpcClient client = CalculatorGrpcClient.getInstance("grpc-hello-world-server", 50051);
        try {
            double result = client.calculate();

            System.out.printf("main result:" + result);
        } finally {
            client.shutdown();
        }
    }
}
