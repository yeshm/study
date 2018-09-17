package study.yeshm.springboot.grpc.context;

import io.grpc.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

/**
 * @author yeshm
 */
@Slf4j
public class GrpcServerInitializedEvent extends ApplicationEvent {
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public GrpcServerInitializedEvent(Server source) {
        super(source);
    }

    public Server getServer() {
        return (Server) getSource();
    }
}
