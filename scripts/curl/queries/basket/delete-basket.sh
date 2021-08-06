#INPUT:

#OUTPUT:
#is removed - BOOLEAN - TRUE

#EXAMPLE:
#SCRIPT INPUT: $1 - JWT - REQUIRED
#sh delete-basket.sh '<JWT>'

query='{ "query": "mutation { deleteBasket }" }'

echo "$query"

curl -X POST \
-H "Content-Type: application/json" \
-H "Authorization: Bearer $1" \
-d "$query" \
http://127.0.0.1/e-shop/api/v1/basket/graphql
