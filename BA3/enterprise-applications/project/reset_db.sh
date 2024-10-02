docker compose -f compose.dev.yaml down
docker volume rm project_api-db
docker compose -f compose.dev.yaml up -d
