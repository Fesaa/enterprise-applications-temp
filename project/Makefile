COMPOSE_FILE=compose.dev.yaml

up:
	docker compose -f $(COMPOSE_FILE) up -d

down:
	docker compose -f $(COMPOSE_FILE) down

clean:
	docker volume rm project_api-db

reset:
	make down
	make clean
	make up

