#INPUT:
#items              - REQUIRED
#items.productId    - REQUIRED
#items.quantity     - REQUIRED
#items.price        - REQUIRED
#items.productName  - REQUIRED

#OUTPUT:
#items              - OPTIONAL
#items.productId    - OPTIONAL
#items.quantity     - OPTIONAL
#items.price        - OPTIONAL
#items.productName  - OPTIONAL

#EXAMPLE:
#SCRIPT INPUT: $1 - INPUT - REQUIRED, $2 - JWT - REQUIRED, $3 - OUTPUT - OPTIONAL
#sh update-basket.sh '<INPUT>' '<JWT>' '<OUTPUT>'

query='{ "query": "mutation { updateBasket(input: {'$1'}) { '

if [ -n "$3" ]
  then
    query+=''$3' } '
  else
    query+=' items { productId, quantity, price, productName } } '  #SET DEFAULT VALUE
fi;

query+=' }" '
query+=' } '

echo "$query"

curl -X POST \
-H "Content-Type: application/json" \
-H "Authorization: Bearer $2" \
-d "$query" \
http://127.0.0.1/e-shop/api/v1/basket/graphql
