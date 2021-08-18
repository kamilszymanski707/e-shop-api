#INPUT:
#username - REQUIRED
#password - REQUIRED

#OUTPUT:
#JWT

#EXAMPLE:
#SCRIPT INPUT: $1 - USERNAME - REQUIRED, $2 - PASSWORD - REQUIRED
#sh keycloak-authorization.sh '<USERNAME>' '<PASSWORD>'

curl --request POST \
  --url http://localhost:8080/auth/realms/e-shop/protocol/openid-connect/token \
  --header 'Content-Type: application/x-www-form-urlencoded' \
  --data client_secret="85e21861-5ff4-4be6-bcdd-94d354fa8d23" \
  --data grant_type=password \
  --data username="$1" \
  --data password="$2" \
  --data client_id=e-shop \
  --data scope=openid
