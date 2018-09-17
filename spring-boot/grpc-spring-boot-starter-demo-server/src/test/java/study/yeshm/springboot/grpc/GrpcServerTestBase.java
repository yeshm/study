package study.yeshm.springboot.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.inprocess.InProcessChannelBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import study.yeshm.grpc.examples.GreeterGrpc;
import study.yeshm.grpc.examples.GreeterOuterClass;
import study.yeshm.springboot.grpc.autoconfigure.GrpcProperties;
import study.yeshm.springboot.grpc.context.LocalRunningGrpcPort;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public abstract class GrpcServerTestBase {

    @Autowired(required = false)
    @Qualifier("grpcServerRunner")
    protected GrpcServerRunner grpcServerRunner;

    @Autowired(required = false)
    @Qualifier("grpcInprocessServerRunner")
    protected GrpcServerRunner grpcInprocessServerRunner;


    protected ManagedChannel channel;
    protected ManagedChannel inProcChannel;

    @LocalRunningGrpcPort
    int runningPort;

    @Autowired
    protected ApplicationContext context;

    @Autowired
    protected GrpcProperties grpcProperties;

    @Before
    public final void setupChannels() {
        if (grpcProperties.getServer().isEnabled()) {
            channel = onChannelBuild(ManagedChannelBuilder.forAddress("localhost", getPort())
                    .usePlaintext()
            ).build();
        }
        if (StringUtils.hasText(grpcProperties.getServer().getInProcessServerName())) {
            inProcChannel = onChannelBuild(
                    InProcessChannelBuilder.forName(grpcProperties.getServer().getInProcessServerName())
                            .usePlaintext()
            ).build();

        }
    }

    protected int getPort() {
        return runningPort;
    }

    protected ManagedChannelBuilder<?> onChannelBuild(ManagedChannelBuilder<?> channelBuilder) {
        return channelBuilder;
    }

    protected InProcessChannelBuilder onChannelBuild(InProcessChannelBuilder channelBuilder) {
        return channelBuilder;
    }

    @After
    public final void shutdownChannels() {
        Optional.ofNullable(channel).ifPresent(ManagedChannel::shutdownNow);
        Optional.ofNullable(inProcChannel).ifPresent(ManagedChannel::shutdownNow);
    }

    @Test
    final public void simpleGreeting() throws ExecutionException, InterruptedException {

        beforeGreeting();
        String name = "John";
        final GreeterGrpc.GreeterFutureStub greeterFutureStub = GreeterGrpc.newFutureStub(Optional.ofNullable(channel).orElse(inProcChannel));
        final GreeterOuterClass.HelloRequest helloRequest = GreeterOuterClass.HelloRequest.newBuilder().setName(name).build();
        final String reply = greeterFutureStub.sayHello(helloRequest).get().getMessage();
        assertNotNull("Replay should not be null", reply);
        assertTrue(String.format("Replay should contain name '%s'", name), reply.contains(name));
        afterGreeting();

    }

    protected void beforeGreeting() {

    }

    protected void afterGreeting() {

    }
}
