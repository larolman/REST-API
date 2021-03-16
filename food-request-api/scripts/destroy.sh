#!/bin/bash -e

ROOT=$(dirname "${BASH_SOURCE[0]}")
# shellcheck disable=SC1090
source "${ROOT}"/constants.sh

gcloud sql instances delete "$INSTANCE_NAME" --quiet

gcloud container clusters delete \
postgres-demo-cluster --zone "$CLUSTER_ZONE" --quiet

gcloud projects remove-iam-policy-binding "$PROJECT" \
--member serviceAccount:"$FULL_SA_NAME" \
--role roles/cloudsql.client > /dev/null

gcloud projects remove-iam-policy-binding "$PROJECT" \
--member serviceAccount:"$FULL_NODE_SA_NAME" \
--role roles/logging.logWriter > /dev/null

gcloud projects remove-iam-policy-binding "$PROJECT" \
--member serviceAccount:"$FULL_NODE_SA_NAME" \
--role roles/monitoring.metricWriter > /dev/null

gcloud projects remove-iam-policy-binding "$PROJECT" \
--member serviceAccount:"$FULL_NODE_SA_NAME" \
--role roles/monitoring.viewer > /dev/null

gcloud iam service-accounts delete "$FULL_SA_NAME" \
--quiet && rm credentials.json

gcloud iam service-accounts delete "$FULL_NODE_SA_NAME" --quiet