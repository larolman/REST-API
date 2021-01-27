MVN=./mvnw
MYSQL_DIR=deploy/mysql
API_DIR=deploy/api

build-api:
	@$(MVN) clean package

build-docker:
	@$(MAKE) build-api
	@docker build -t larolman/food-request-api .

push-docker-image:
	@docker login
	@docker push $(DOCKER_REPO)/food-request-api:latest

start-api:
	@echo " --- STARTING FOOD REQUEST API --- "
	@docker-compose -f docker-compose.yml up --build food-request

stop-api:
	@echo " --- STOPPING FOOD REQUEST API --- "
	@docker-compose -f docker-compose.yml down -v

deploy-mysql:
	@echo " --- DEPLOYING MYSQL 5.7 TO K8s --- "
	@kubectl apply -f $(MYSQL_DIR)

deploy-api:
	@echo " --- DEPLOYING FOOD REQUEST API TO K8s --- "
	@kubectl apply -f $(API_DIR)

destroy-k8:
	@echo " --- DESTROYING K8s RESOURCES --- "
	@kubectl delete -f $(API_DIR) || true
	@kubectl delete -f $(MYSQL_DIR) || true