#!/bin/bash -e

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