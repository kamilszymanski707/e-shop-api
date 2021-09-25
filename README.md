# e-shop-api

A system used to manage a shopping cart.

How to run it:
1. Download e-shop-api: "git clone https://github.com/kamilszymanski707/e-shop-api.git"
2. Run the file the runner.py with parameter local - localhost or docker
3. In your browser go to the keycloak admin panel: "http://localhost:8080/auth" and log in as administrator: username and password are: "admin"
4. In realm, in client "e-shop" create users with roles "admin" or "user"
5. If you don't have curl installed you can do it with a script: "sh ./e-shop-api/scripts/curl/curl.sh"
6. All requests to services are contained in files in the folder: "/e-shop-api/scripts/curl/queries/{selected service}"
7. If you preview any of the scripts using notepad you will see instructions on how to use 
8. If you want to run the system locally:
   - Follow all the steps that were described before
   - Create the databases, ELK and keycloak according to the file "/e-shop-api/docker/docker-compose.yml".
   - Run the projects according to the order: "eureka", "config", "gateway", {selected services}
9. All logins and passwords related to specific databases and keycloak are in the file "/e-shop-api/docker/docker-compose.yml"

The system is built on:
Kotlin, Spring Cloud, GraphQL, Logstash, Keycloak, RabbitMQ, MongoDB, PostgreSQL, Redis, JPA and Docker.
