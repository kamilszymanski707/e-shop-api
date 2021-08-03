#INPUT:
#client secret - REQUIRED
#username - REQUIRED
#password - REQUIRED

#OUTPUT:
#JWT

#EXAMPLE:
#SCRIPT INPUT: $1 - SECRET - REQUIRED, $2 - USERNAME - REQUIRED, $3 - PASSWORD - REQUIRED
#sh keycloak-authorization.sh '<SECRET>' '<USERNAME>' '<PASSWORD>'

curl --request POST \
  --url http://localhost:8080/auth/realms/e-shop/protocol/openid-connect/token \
  --header 'Content-Type: application/x-www-form-urlencoded' \
  --data client_secret="$1" \
  --data grant_type=password \
  --data username="$2" \
  --data password="$3" \
  --data client_id=e-shop \
  --data scope=openid
