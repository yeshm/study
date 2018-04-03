## Basic Authentication

This example shows how to add authentication in a Ingress rule using a secret that contains a file generated with htpasswd.

1.htpasswd -c auth foo


2.kubectl create secret generic basic-auth --from-file=auth

3.create Ingress

<pre>
echo "
apiVersion: extensions/v1beta1
kind: Ingress
metadata:
  name: ingress-with-auth
  annotations:
    # type of authentication
    nginx.ingress.kubernetes.io/auth-type: basic
    # name of the secret that contains the user/password definitions
    nginx.ingress.kubernetes.io/auth-secret: basic-auth
    # message to display with an appropriate context why the authentication is required
    nginx.ingress.kubernetes.io/auth-realm: "Authentication Required - foo"
spec:
  rules:
  - host: foo.bar.com
    http:
      paths:
      - path: /
        backend:
          serviceName: http-svc
          servicePort: 80
" | kubectl create -f -
</pre>

4.curl -v http://10.2.29.4/ -H 'Host: foo.bar.com'

5.curl -v http://10.2.29.4/ -H 'Host: foo.bar.com' -u 'foo:bar'