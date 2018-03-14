1.Create a ConfigMap containing multiple key-value pairs.

<pre>
apiVersion: v1
kind: ConfigMap
metadata:
  name: special-config
  namespace: default
data:
  SPECIAL_LEVEL: very
  SPECIAL_TYPE: charm
</pre>

2.Use envFrom to define all of the ConfigMap’s data as Pod environment variables. The key from the ConfigMap becomes the environment variable name in the Pod.

<pre>
apiVersion: v1
kind: Pod
metadata:
  name: dapi-test-pod
spec:
  containers:
    - name: test-container
      image: k8s.gcr.io/busybox
      command: [ "/bin/sh", "-c", "env" ]
      envFrom:
      - configMapRef:
          name: special-config
  restartPolicy: Never
</pre>

3.Save the changes to the Pod specification. Now, the Pod’s output includes SPECIAL_LEVEL=very and SPECIAL_TYPE=charm.

