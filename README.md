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

## Release Docker
`./release.sh` it creates the build and launch the docker environment

Note: requires `docker compose` not `docker-compose`. Change properly the `release.sh` script in case your base system does not support it.