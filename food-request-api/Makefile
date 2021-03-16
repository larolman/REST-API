MVN=./mvnw
API_DIR=deploy/api
CONSTANTS=scripts/constants.sh
CLUSTER=scripts/cluster.sh
CONFIG=scripts/configs_secrets.sh
ENABLE_APIS=scripts/enable_apis.sh
MYSQL_INSTANCE=scripts/mysql_instance.sh
SERVICE_ACC=scripts/service_account.sh
SET_ZONE_REGION=scripts/set_zone_region.sh
DESTROY=scripts/destroy.sh

build-api:
	@ $(MVN) clean package

build-docker:
	@ $(MAKE) build-api
	@ docker build -t gcr.io/${PROJECT}/food-request-api .

push-docker-image:
	@ docker push gcr.io/${PROJECT}/food-request-api:latest

deploy-api:
	@ echo " --- DEPLOYING FOOD REQUEST API TO K8s --- "
	@ kubectl apply -f $(API_DIR)

destroy-k8s:
	@ echo " --- DESTROYING K8s RESOURCES --- "
	@ kubectl delete -f $(API_DIR) || true

create-infra:
	@ echo " --- DEPLOYING INFRASTRUCTURE --- "
	@ chmod +x scripts/*.sh
	@ $(SET_ZONE_REGION)
	@ $(ENABLE_APIS)
	@ $(MYSQL_INSTANCE)
	@ $(CLUSTER)
	@ $(CONFIG)
	@ sed -i "s/\/project\//\/$(PROJECT)\//g" deploy/api/food-request-api.yml

destroy-infra:
	@ echo " --- DESTROYING K8s RESOURCES --- "
	@ $(DESTROY)