MVN=./mvnw
API_DIR=deploy/api

build-api:
	@$(MVN) clean package

build-docker:
	@$(MAKE) build-api
	@docker build -t gcr.io/${PROJECT_ID}/food-request-api .

push-docker-image:
	@docker push gcr.io/${PROJECT_ID}/food-request-api:latest

deploy-api:
	@echo " --- DEPLOYING FOOD REQUEST API TO K8s --- "
	@kubectl apply -f $(API_DIR)

destroy-k8:
	@echo " --- DESTROYING K8s RESOURCES --- "
	@kubectl delete -f $(API_DIR) || true