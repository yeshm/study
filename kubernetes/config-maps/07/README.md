
When a ConfigMap already being consumed in a volume is updated, projected keys are eventually updated as well. Kubelet is checking whether the mounted ConfigMap is fresh on every periodic sync. However, it is using its local ttl-based cache for getting the current value of the ConfigMap. As a result, the total delay from the moment when the ConfigMap is updated to the moment when new keys are projected to the pod can be as long as kubelet sync period + ttl of ConfigMaps cache in kubelet.

    Note: A container using a ConfigMap as a subPath volume will not receive ConfigMap updates.

####备注：
1. pod运行后查看日志
2. 修改ConfigMap里special.level的值，过一会日志就会根据修改的值变化