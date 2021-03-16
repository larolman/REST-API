#!/bin/bash -e

ROOT=$(dirname "${BASH_SOURCE[0]}")
# shellcheck disable=SC1090
source "${ROOT}"/constants.sh

gcloud iam service-accounts create "$SA_NAME" --display-name "$SA_NAME"

# This is the policy for the container that will communicate with Cloud SQL Proxy
# The only permissions it needs is roles/cloudsql.client
# Remember, least privilege
gcloud projects add-iam-policy-binding "$PROJECT" \
--member serviceAccount:"$FULL_SA_NAME" \
--role roles/cloudsql.client > /dev/null

gcloud iam service-accounts keys create credentials.json --iam-account "$FULL_SA_NAME"