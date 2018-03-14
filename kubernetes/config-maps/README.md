# ConfigMaps
    https://kubernetes.io/docs/tasks/configure-pod-container/configure-pod-configmap/ 学习记录

01 Define a Pod environment variable with data from a single ConfigMap

02 Define Pod environment variables with data from multiple ConfigMaps

03 Configure all key-value pairs in a ConfigMap as Pod environment variables

04 Use ConfigMap-defined environment variables in Pod commands

05 Add ConfigMap data to a Volume

06 Add ConfigMap data to a specific path in the Volume

07 Mounted ConfigMaps are updated automatically

####busybox介绍
BusyBox 是一个集成了三百多个最常用Linux命令和工具的软件。BusyBox 包含了一些简单的工具，例如ls、cat和echo等等，还包含了一些更大、更复杂的工具，例grep、find、mount以及telnet。有些人将 BusyBox 称为 Linux 工具里的瑞士军刀。简单的说BusyBox就好像是个大工具箱，它集成压缩了 Linux 的许多工具和命令，也包含了 Android 系统的自带的shell。