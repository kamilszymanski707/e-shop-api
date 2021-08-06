#INPUT:

#OUTPUT:
#items                - OPTIONAL
#items.productId      - OPTIONAL
#items.quantity       - OPTIONAL
#items.price          - OPTIONAL
#items.productName    - OPTIONAL

#EXAMPLE:
#SCRIPT INPUT: $1 - JWT - REQUIRED, $2 - OUTPUT - OPTIONAL
#sh get-basket.sh '<JWT>' '<OUTPUT>'

query='{ "query": "query { getBasket { '

#OUTPUT - OPTIONAL
if [ -n "$2" ]
  then
    query+=''$2' } '
  else
    query+=' items { productId, quantity, price, productName } } '  #SET DEFAULT VALUE
fi;

query+=' }" '
query+=' } '

echo "$query"

curl -X POST \
-H "Content-Type: application/json" \
-H "Authorization: Bearer $1" \
-d "$query" \
http://127.0.0.1/e-shop/api/v1/basket/graphql
