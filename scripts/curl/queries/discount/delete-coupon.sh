#INPUT:
#productId       - REQUIRED

#OUTPUT:
#is removed - BOOLEAN - TRUE

#EXAMPLE:
#SCRIPT INPUT: $1 - INPUT - REQUIRED, $2 - JWT - REQUIRED
#sh delete-coupon.sh '<INPUT>' '<JWT>'

query='{ "query": "mutation { deleteCoupon(productId: '$1') }" }'

echo "$query"

curl -X POST \
-H "Content-Type: application/json" \
-H "Authorization: Bearer $2" \
-d "$query" \
http://127.0.0.1/e-shop/api/v1/discount/graphql
