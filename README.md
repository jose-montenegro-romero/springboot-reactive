# springboot-reactive
Spring boot reactivo.

# Command build with gradle
./gradlew clean build -x test
./gradlew clean build

# Docker commands
docker build -t java21-app:latest .
docker run -p 8080:8080 java21-app:latest