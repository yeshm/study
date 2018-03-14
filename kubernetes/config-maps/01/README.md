1.Define an environment variable as a key-value pair in a ConfigMap:
>kubectl create configmap special-config --from-literal=special.how=very 

2.Assign the special.how value defined in the ConfigMap to the SPECIAL_LEVEL_KEY environment variable in the Pod specification.
>'kubectl edit pod dapi-test-pod

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
      env:
        # Define the environment variable
        - name: SPECIAL_LEVEL_KEY
          valueFrom:
            configMapKeyRef:
              # The ConfigMap containing the value you want to assign to SPECIAL_LEVEL_KEY
              name: special-config
              # Specify the key associated with the value
              key: special.how
  restartPolicy: Never'
</pre> 
3.Save the changes to the Pod specification. Now, the Pod’s output includes SPECIAL_LEVEL_KEY=very.

