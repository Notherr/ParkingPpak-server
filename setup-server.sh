
./gradlew clean
./gradlew build -x test

docker compose down
docker volume prune -f --force
docker rmi parkingppak
docker compose up -d


