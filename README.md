# java-spring-reactive
The idea is to implement a simple project using the reactive stack including:
- Rest controller with mono (async promise like event loop)
- Jwt token auth using spring-security
- Authorize route using @PreAuthorize("hasRole")
- TODO

## Java spring reactive stack
- Spring Security Reactive
- Spring WebFlux
- Spring Data Reactive Repositories

## Start
`./gradlew bootRun`

## Test
`./gradlew test` it launches all classes annotated with `@SpringBootTest` and its `@Test` methods

## Release
- change version inside `build.gradle` 
- `./gradlew build` (launch automatically tests)
- `java -jar build/libs/reactivestack-version.jar --spring.profiles.active=prod`

## Docker
docker run --name java-spring-reactive-mongo -d mongo:tag