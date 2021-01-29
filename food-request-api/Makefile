MVN=./mvnw
MYSQL_DIR=deploy/mysql
API_DIR=deploy/api
KAFKA_DIR=deploy/kafka

build-api:
	@$(MVN) clean package

build-docker:
	@$(MAKE) build-api
	@docker build -t larolman/food-request-api:kafka .

push-docker-image:
	@docker login
	@docker push $(DOCKER_REPO)/food-request-api:kafka

start-api:
	@echo " --- STARTING FOOD REQUEST API --- "
	@docker-compose -f docker-compose.yml up --build food-request

stop-api:
	@echo " --- STOPPING FOOD REQUEST API --- "
	@docker-compose -f docker-compose.yml down -v

deploy-infra:
	@echo " --- DEPLOYING MYSQL 5.7 TO K8s --- "
	@kubectl apply -f $(MYSQL_DIR)
	@echo " --- DEPLOYING KAFKA STACK 5.3.1 TO K8s --- "
	@kubectl apply -f $(KAFKA_DIR)
	@sleep 10

deploy-api:
	@echo " --- DEPLOYING FOOD REQUEST API TO K8s --- "
	@kubectl apply -f $(API_DIR)

destroy-k8s:
	@echo " --- DESTROYING K8s RESOURCES --- "
	@kubectl delete -f $(API_DIR) || true
	@kubectl delete -f $(MYSQL_DIR) || true
	@kubectl delete -f $(KAFKA_DIR) || true
