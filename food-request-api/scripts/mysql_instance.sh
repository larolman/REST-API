#!/bin/bash -e

ROOT=$(dirname "${BASH_SOURCE[0]}")
# shellcheck disable=SC1090
source "${ROOT}"/constants.sh

RANDOM_SUFFIX=$(tr -dc 'a-z0-9' </dev/urandom | fold -w 6 | head -n 1)
export INSTANCE_NAME=food-request-mysql-${RANDOM_SUFFIX}

gcloud sql instances create "$INSTANCE_NAME" \
--database-version MYSQL_5_7 \
--region "$INSTANCE_REGION" \
--root-password=3luproec \
--tier db-f1-micro \
--availability-type=zonal \
--replica-type=READ \
--storage-type HDD \
--async

echo "Cloud SQL instance creation started. This process takes a long time (5-10min)."