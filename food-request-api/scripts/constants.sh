PROJECT=$(gcloud config get-value core/project)
export PROJECT
CLUSTER_ZONE=$(gcloud config get-value compute/zone)
export CLUSTER_ZONE
export CLUSTER_NAME=gke-demo

INSTANCE_REGION=$(gcloud config get-value compute/region)
export INSTANCE_REGION
export SA_NAME=food-request-demo-sa
export FULL_SA_NAME=$SA_NAME@$PROJECT.iam.gserviceaccount.com