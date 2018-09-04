#!/usr/bin/env sh

##############################################################################
##
##  build hello-world-server image
##
##############################################################################

gradle -b ../build.gradle clean
gradle -b ../build.gradle installDist

docker build -t yeshuangming/grpc-hello-world-server:0.0.1 -f ./DockerfileServer ..