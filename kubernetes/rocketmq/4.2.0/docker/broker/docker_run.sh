#!/bin/bash
docker run -d -p 10911:10911 -p 10909:10909 --name rmqbroker yeshuangming/rocketmq-broker:4.2.0
