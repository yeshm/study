package study.yeshm.springboot.grpc.autoconfigure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @author yeshm
 */
@ConfigurationProperties(prefix = "grpc", ignoreUnknownFields = true)
@Getter
@Setter
public class GrpcProperties {

    public static final int DEFAULT_GRPC_PORT = 50051;

    @NestedConfigurationProperty
    private final ServerProperties server = new ServerProperties();

    @Getter
    @NestedConfigurationProperty
    private final StubProperties stub = new StubProperties();

    @Getter
    @Setter
    public class ServerProperties {

        /**
         * gRPC server port
         */
        private int port = DEFAULT_GRPC_PORT;

        /**
         * Enables the embedded grpc server.
         */
        private boolean enabled = true;


        /**
         * In process server name.
         * If  the value is not empty, the embedded in-process server will be created and started.
         */
        private String inProcessServerName;

        /**
         * Enables server reflection using <a href="https://github.com/grpc/grpc-java/blob/master/documentation/server-reflection-tutorial.md">ProtoReflectionService</a>.
         * Available only from gRPC 1.3 or higher.
         */
        private boolean enableReflection = false;

    }

    @Getter
    @Setter
    public class StubProperties {

        /**
         * gRPC stub host
         */
        private String host = "";

        /**
         * gRPC stub port
         */
        private int port = DEFAULT_GRPC_PORT;

    }
}
