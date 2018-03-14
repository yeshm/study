Use the <b>path</b> field to specify the desired file path for specific ConfigMap items. In this case, the <b>special.level</b> item will be mounted in the <b>config-volume</b> volume at <b>/etc/config/keys</b>.

<pre>
apiVersion: v1
kind: Pod
metadata:
  name: dapi-test-pod
spec:
  containers:
    - name: test-container
      image: k8s.gcr.io/busybox
      command: [ "/bin/sh","-c","cat /etc/config/keys" ]
      volumeMounts:
      - name: config-volume
        mountPath: /etc/config
  volumes:
    - name: config-volume
      configMap:
        name: special-config
        items:
        - key: special.level
          path: keys
  restartPolicy: Never
</pre>

When the pod runs, the command ("cat /etc/config/keys") produces the output below:
    
    very