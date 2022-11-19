
./gradlew clean
./gradlew build -x test

docker compose down
docker rmi parkingppak
docker compose up -d


