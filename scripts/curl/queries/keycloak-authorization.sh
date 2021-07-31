#INPUT:
#1 - username
#2 - password

#OUTPUT:
#JWT

#EXAMPLE:
#sh keycloak-authorization.sh user user

curl --request POST \
  --url http://localhost:8080/auth/realms/e-shop/protocol/openid-connect/token \
  --header 'Content-Type: application/x-www-form-urlencoded' \
  --data client_secret=1e8b086c-1399-47c0-bd6b-10f0dc13cee0 \
  --data grant_type=password \
  --data username="$1" \
  --data password="$2" \
  --data client_id=e-shop \
  --data scope=openid
