# e-shop-api

A system used to manage a shopping cart.

How to run it:
1. Create a folder where all necessary components will be located
2. Enter the created folder and open it with bash console
3) Download cloud-core: "git clone https://github.com/kamilszymanski707/cloud-core.git"
4. Download e-shop-api: "git clone https://github.com/kamilszymanski707/e-shop-api.git"
5. Depending on how you want to run the projects:
    - Change "frontendUrl" property in "/e-shop-api/docker/tmp/realm-export.json" according to "/e-shop-api/docker/tmp/frontendUrl.txt"
6. Run the script "sh ./e-shop-api/scripts/mvn-build.sh" - it builds all applications , installs needed libraries and copies files needed for ELK stack and cloud config.
7. Run the file: "/e-shop-api/docker/docker-compose.yml" using the command: "docker-compose up"
8. In your browser go to the keycloak admin panel: "http://localhost:8080/auth" and log in as administrator: username and password are: "admin"
9. In realm, in client "e-shop" create users with roles "admin" or "user"
10. If you don't have curl installed you can do it with a script: "sh ./e-shop-api/scripts/curl/curl.sh"
11. All requests to services are contained in files in the folder: "/e-shop-api/scripts/curl/queries/{selected service}"
12. If you preview any of the scripts using notepad you will see instructions on how to use 
13. If you want to run the system locally:
    - Follow all the steps that were described before
    - Create the databases, ELK and keycloak according to the file "/e-shop-api/docker/docker-compose.yml".
    - Run the projects according to the order: "eureka", "config", "gateway", {selected services}
14. All logins and passwords related to specific databases and keycloak are in the file "/e-shop-api/docker/docker-compose.yml"

The system is built on:
Kotlin, Spring Cloud, GraphQL, Logstash, Keycloak, RabbitMQ, MongoDB, PostgreSQL, Redis, JPA and Docker.
