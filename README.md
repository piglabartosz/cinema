# INSTALLATION GUIDE

Create environment variable with key: OMDB_KEY and your API key as value. Docker Compose needs it to pass API key as
environment variable to cinema-api container.

```
./gradlew build -x test
docker-compose build
docker-compose up
```

PostgreSQL container uses default port so make sure that it is not already in use.

If you want to use debugger in IntelliJ IDEA and Docker Compose, you can use this tutorial:
https://www.jetbrains.com/help/idea/run-and-debug-a-spring-boot-application-using-docker-compose.html

# INTEGRATION TESTS

If you want to use singleton containers for better performance, add `testcontainers.reuse.enable=true` property
to `.testcontainers.properties` file in your home directory. Details
here: https://www.testcontainers.org/test_framework_integration/manual_lifecycle_control/#singleton-containers
