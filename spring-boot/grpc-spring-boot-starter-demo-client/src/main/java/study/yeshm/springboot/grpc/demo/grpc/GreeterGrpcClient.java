/*
 * Copyright 2015 The gRPC Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package study.yeshm.springboot.grpc.demo.grpc;

import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import study.yeshm.grpc.examples.GreeterGrpc;
import study.yeshm.grpc.examples.GreeterOuterClass;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GreeterGrpcClient {
  private static final Logger logger = Logger.getLogger(GreeterGrpcClient.class.getName());

  private final ManagedChannel channel;
  private final GreeterGrpc.GreeterBlockingStub blockingStub;

  private static volatile GreeterGrpcClient instance;

  public static GreeterGrpcClient getInstance(String host, int port){
      if (instance == null) {
          synchronized (GreeterGrpcClient.class) {
              if (instance == null) {
                  instance = new GreeterGrpcClient(host, port);
              }
          }
      }

      return instance;
  }

  /** Construct client connecting to HelloWorld server at {@code host:port}. */
  public GreeterGrpcClient(String host, int port) {
    this(GrpcHelper.getChannel(host, port));
  }

  /** Construct client for accessing HelloWorld server using the existing channel. */
  GreeterGrpcClient(ManagedChannel channel) {
    this.channel = channel;
    blockingStub = GreeterGrpc.newBlockingStub(channel);
  }

  public void shutdown() throws InterruptedException {
    channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
  }

  /** Say hello to server. */
  public String greet(String name) {
    logger.info("Will try to greet " + name + " ...");
    GreeterOuterClass.HelloRequest request = GreeterOuterClass.HelloRequest.newBuilder().setName(name).build();
    GreeterOuterClass.HelloReply response;
    try {
      response = blockingStub.sayHello(request);
    } catch (StatusRuntimeException e) {
      logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
      return null;
    }
    logger.info("Greeting: " + response.getMessage());
    return response.getMessage();
  }

  /**
   * Greet server. If provided, the first element of {@code args} is the name to use in the
   * greeting.
   */
  public static void main(String[] args) throws Exception {
    GreeterGrpcClient client = new GreeterGrpcClient("grpc-hello-world-server", 50051);
    try {
      /* Access a service running on the local machine on port 50051 */
      String user = "world";
      if (args.length > 0) {
        user = args[0]; /* Use the arg as the name to greet if provided */
      }
      client.greet(user);

      System.out.printf("shutting down client...");
    } finally {
      client.shutdown();
    }
  }
}
