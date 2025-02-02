FROM amazoncorretto:17-alpine3.15-jdk as build
WORKDIR /build
RUN apk add maven=3.8.3-r0
COPY pom.xml .

RUN mvn org.apache.maven.plugins:maven-help-plugin:3.2.0:evaluate -Dexpression=project.artifactId -q -DforceStdout > artifactId
RUN mvn org.apache.maven.plugins:maven-help-plugin:3.2.0:evaluate -Dexpression=project.version -q -DforceStdout > version

COPY . .
RUN mvn clean package -DskipTests

RUN mv ./target/`cat artifactId`-`cat version`.jar ./application.jar

FROM amazoncorretto:17-alpine3.15-jdk as runner

WORKDIR /app

COPY --from=build /build/application.jar ./application.jar

CMD ["java", "-jar", "application.jar"]