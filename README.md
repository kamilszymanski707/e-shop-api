# e-shop-api

A system used to manage a shopping cart.

To run this, release the script "/scripts/mvn-build.sh" which builds all applications using maven. Then run the "
docker-compose up" command in the "/docker" folder. In the next step you need to add users to keycloak. To do this you
need to go to the login panel in your browser: "http://localhost:8080/auth"
log in as administrator: username: "admin", password: "admin" change realm to "e-shop" and add users with appropriate
roles, passwords etc. to "e-shop" client.

Requests can be made using prepared scripts that include usage examples. To use them, go to the folder: "/scripts/curl"
and if you do not have curl installed you can do it with the curl.sh script. Then with command line go to folder: "
/scripts/curl/queries" where you can find scripts for calling services. All scripts have proper comments for API
handling.

The system is built on:
Kotlin, Spring Cloud, GraphQL, Logstash, Keycloak, RabbitMQ, MongoDB, PostgreSQL, Redis, JPA and Docker.

If you want to view the logs you can go to this address in your browser: "http://localhost:5601" and log in as
administrator: login: "logstash", password: "w9rnPfnXmnh6Wy3a".
