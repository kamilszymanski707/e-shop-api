#INPUT:
#id       - REQUIRED

#OUTPUT:
#is removed - BOOLEAN - TRUE

#EXAMPLE:
#SCRIPT INPUT: $1 - INPUT - REQUIRED, $2 - JWT - REQUIRED
#sh delete-product.sh '<INPUT>' '<JWT>'

query='{ "query": "mutation { deleteProduct(id: '$1') }" }'

echo "$query"

curl -X POST \
-H "Content-Type: application/json" \
-H "Authorization: Bearer $2" \
-d "$query" \
-k https://127.0.0.1/e-shop/api/v1/catalog/graphql
