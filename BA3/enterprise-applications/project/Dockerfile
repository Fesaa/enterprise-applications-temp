FROM node:18 AS npm-stage

WORKDIR /app

COPY UI/Web/package.json UI/Web/package-lock.json ./
RUN npm install

COPY UI/Web ./

RUN npm run build

FROM eclipse-temurin:23 AS builder

COPY API/*.gradle gradle.* API/gradlew /src/
COPY API/gradle /src/gradle

WORKDIR /src
RUN ./gradlew --version

COPY API/. .
COPY --from=npm-stage /app/dist/web/browser ./src/main/resources/public/

RUN ls -la ./src/main/resources/
RUN ls -la ./src/main/resources/public

RUN ./gradlew build


FROM eclipse-temurin:23

COPY --from=builder /src/build/libs/API-0.0.1-SNAPSHOT.jar /app.jar

HEALTHCHECK CMD curl --fail http://0.0.0.0:8080/health || exit 1

ENTRYPOINT ["java","-jar","/app.jar"]
