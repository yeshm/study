package study.yeshm.springboot.grpc;

import org.junit.Assert;
import org.junit.runner.RunWith;
import study.yeshm.springboot.grpc.context.LocalRunningGrpcPort;
import study.yeshm.springboot.grpc.demo.DemoApp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DemoApp.class}, webEnvironment = NONE, properties = "grpc.server.port=0")
public class RandomGrpcPortTest extends GrpcServerTestBase {

    @Value("${local.grpc.port}")
    int port;

    @LocalRunningGrpcPort
    int runningPort;

    @Override
    protected int getPort() {
        return port;
    }

    @Override
    protected void beforeGreeting() {
        Assert.assertEquals(0, grpcProperties.getServer().getPort());
        Assert.assertEquals(port,runningPort);
    }
}
