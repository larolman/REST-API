#!/bin/bash -e

# Here we are installing secrets into the Kubernetes cluster
# Installing them into the cluster makes it very easy to access them from
# the appliations in the cluster
kubectl --namespace default create secret generic cloudsql-sa-creds \
--from-file=credentials.json=credentials.json

INSTANCE_NAME=$(gcloud sql instances list --format="value(name)")

CONNECTION_NAME=$(gcloud sql instances describe "$INSTANCE_NAME" \
--format="value(connectionName)")

# You can also store non-sensitive information in the cluster
# similar to a secret. In this case we are using a configMap, which
# is also very easy to access from applications in the cluster
kubectl --namespace default create configmap connectionname \
--from-literal=connectionname="$CONNECTION_NAME"