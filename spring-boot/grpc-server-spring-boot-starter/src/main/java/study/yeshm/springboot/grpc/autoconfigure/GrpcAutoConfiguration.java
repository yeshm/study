package study.yeshm.springboot.grpc.autoconfigure;

import io.grpc.ServerBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.services.HealthStatusManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import study.yeshm.springboot.grpc.GrpcServerBuilderConfigurer;
import study.yeshm.springboot.grpc.GrpcServerRunner;
import study.yeshm.springboot.grpc.GrpcService;
import study.yeshm.springboot.grpc.context.LocalRunningGrpcPort;

/**
 * @author yeshm
 */
@AutoConfigureOrder
@ConditionalOnBean(annotation = GrpcService.class)
@EnableConfigurationProperties(GrpcProperties.class)
public class GrpcAutoConfiguration {

    @LocalRunningGrpcPort
    private int port;

    @Autowired
    private GrpcProperties grpcProperties;

    @Bean
    @ConditionalOnProperty(value = "grpc.server.enabled", havingValue = "true", matchIfMissing = true)
    public GrpcServerRunner grpcServerRunner(GrpcServerBuilderConfigurer configurer) {
        return new GrpcServerRunner(configurer, ServerBuilder.forPort(port));
    }

    @Bean
    @ConditionalOnExpression("#{environment.getProperty('grpc.server.inProcessServerName','')!=''}")
    public GrpcServerRunner grpcInprocessServerRunner(GrpcServerBuilderConfigurer configurer) {
        return new GrpcServerRunner(configurer, InProcessServerBuilder.forName(grpcProperties.getServer().getInProcessServerName()));
    }

    @Bean
    public HealthStatusManager healthStatusManager() {
        return new HealthStatusManager();
    }

    @Bean
    @ConditionalOnMissingBean(GrpcServerBuilderConfigurer.class)
    public GrpcServerBuilderConfigurer serverBuilderConfigurer() {
        return new GrpcServerBuilderConfigurer();
    }
}
