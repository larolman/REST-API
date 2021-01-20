MVN=./mvnw

build-api:
	$(MVN) clean package

start-api:
	@echo " --- STARTING FOOD REQUEST API--- "
	@docker-compose -f docker-compose.yml up --build food-request

stop-api:
	@echo " --- STOPPING FOOD REQUEST API--- "
	@docker-compose -f docker-compose.yml down -v