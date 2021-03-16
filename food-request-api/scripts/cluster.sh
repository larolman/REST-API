#!/bin/bash -e

ROOT=$(dirname "${BASH_SOURCE[0]}")
# shellcheck disable=SC1090
source "${ROOT}"/constants.sh

gcloud container clusters create "$CLUSTER_NAME" \
--num-nodes 1 \
--enable-autorepair \
--zone "$CLUSTER_ZONE" \
--additional-zones="$ADDITIONAL_CLUSTER_ZONE" \
--service-account="food-request-demo-node-sa@$PROJECT".iam.gserviceaccount.com \

# Setting up .kube/config. This happens normally if you don't use --async
gcloud container clusters get-credentials "$CLUSTER_NAME" --zone "$CLUSTER_ZONE"