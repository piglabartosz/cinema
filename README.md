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

# API DOCUMENTATION

Swagger 2.0 documentation: http://localhost:8080/swagger-ui/

# EXERCISE SUMMARY

I decided to implement these 2 endpoints:

- An internal endpoint in which they (i.e. the cinema owners) can update show times and prices for their movie catalog
- An endpoint in which their customers (i.e. moviegoers) can fetch details about one of their movies (e.g. name,
  description, release date, rating, IMDb rating, and runtime). Even though there's a limited offering, please use the
  OMDb APIs (detailed below) to demonstrate how to communicate across APIs. I selected them because I think that they
  are the most challenging, and they verify most of the skills.

# BUSINESS LOGIC

## SHOW TIME

I decided to implement conflict detection because 2 show times cannot occur in the same time in the same screening room.
We should trust that client provides consistent data. Things I could do better:

- detecting if show time period is enough for runtime of given movie
- detecting if there is enough gap between show times - we should have time for cleaning screening rooms before next
  show time

# ARCHITECTURE

## DATABASE

I chose relational database because I wanted to use transaction for scheduling show time in screening room.

## TESTING

Unit tests for value objects like money or show time period and domain objects like screening room. I used integration
tests for controllers in order to verify endpoint responses and make sure that all data are saved in database.

## DEPLOYMENT

I used Docker Compose for development purposes. Thanks to it, it is easier to run this service locally. Other developers
will not have to install database and other dependencies which can occur in the near future. Creating CI/CD pipelines
and Kubernetes setup is out of scope for this exercise. Storing secrets should also be improved. We shouldn't keep
production passwords e.g. in docker-compose.yml file because this file is accessible to everyone who has access to this
repository.

# WHAT SHOULD WE DO NEXT?

1. We should add these endpoints:
    - all endpoints mentioned in https://gist.github.com/wbaumann/aaa5ef095e213ffbea35b7ca3cc251a7
      which I did not do in this iteration.
    - endpoints performing CRUD operations for movie, screening room, show times
    - endpoints for submitting and removing customer rate to movie
    - change endpoint for updating and submitting show times - add support for recurring show times, e.g. show time
      every Monday at 8:00 p.m
2. Add security to endpoints. We can use social login, OAuth 2.0 module from Spring Boot and existing authorization
   servers like Google. I wouldn't implement JWT issuing on my own. Implementing it in secure way is quite challenging
   because of all issues described here:
   https://cheatsheetseries.owasp.org/cheatsheets/JSON_Web_Token_for_Java_Cheat_Sheet.html
   Instead of doing it, I would use social login or some existing authorization server (e.g. Keycloak). If we are going
   to use API gateway, we can think about moving authentication to this element of the system. Then we can focus only on
   implementing authorization in this service. I see at least 2 roles in this service:
    - manager - administrates show times and screening room
    - customer - gets movie details, gets show times and similar things
3. Performance improvements:
    - creating job for removing old show times - this table will contain much data. Introducing recurring show times
      could fix this issue.
    - adding 2nd level cache for screening room table
    - we can think about introducing GraphQL if there are a lot of clients (like browser or Android app)
      and each of them require other data from endpoint responses
