#!/usr/bin/env sh

##############################################################################
##
##  build hello-world-client image
##
##############################################################################

gradle -b ../build.gradle clean
gradle -b ../build.gradle installDist

docker build -t yeshuangming/grpc-hello-world-client:0.0.1 -f ./DockerfileClient ..