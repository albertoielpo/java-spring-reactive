./gradlew clean
./gradlew build
cp build/libs/reactivestack-1.0.0.jar docker/reactivestack.jar
docker compose stop
docker compose down
docker compose build
docker compose up -d