#!/bin/bash -e

ROOT=$(dirname "${BASH_SOURCE[0]}")
# shellcheck disable=SC1090
source "${ROOT}"/constants.sh

gcloud iam service-accounts create "$SA_NAME" --display-name "$SA_NAME"

gcloud iam service-accounts create "$NODE_SA_NAME" --display-name "$NODE_SA_NAME"

# This is the policy for the container that will communicate with Cloud SQL Proxy
# The only permissions it needs is roles/cloudsql.client
# Remember, least privilege
gcloud projects add-iam-policy-binding "$PROJECT" \
--member serviceAccount:"$FULL_SA_NAME" \
--role roles/cloudsql.client > /dev/null

# We are building a low privilege service account for the GKE nodes
# The actual privileged SAs are built on a per-container basis
# These three privileges are the minimum needed for a functioning node
# per the GKE docs
# https://cloud.google.com/kubernetes-engine/docs/how-to/hardening-your-cluster#use_least_privilege_service_accounts_for_your_nodes
gcloud projects add-iam-policy-binding "$PROJECT" \
--member serviceAccount:"$FULL_NODE_SA_NAME" \
--role roles/logging.logWriter > /dev/null

gcloud projects add-iam-policy-binding "$PROJECT" \
--member serviceAccount:"$FULL_NODE_SA_NAME" \
--role roles/monitoring.metricWriter > /dev/null

gcloud projects add-iam-policy-binding "$PROJECT" \
--member serviceAccount:"$FULL_NODE_SA_NAME" \
--role roles/monitoring.viewer > /dev/null

gcloud projects add-iam-policy-binding "$PROJECT" \
--member serviceAccount:"$FULL_NODE_SA_NAME" \
--role roles/storage.objectViewer > /dev/null