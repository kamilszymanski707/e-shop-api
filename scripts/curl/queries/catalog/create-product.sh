#INPUT:
#name     - REQUIRED
#category - REQUIRED
#price    - REQUIRED

#OUTPUT:
#id       - OPTIONAL
#name     - OPTIONAL
#category - OPTIONAL
#price    - OPTIONAL

#EXAMPLE:
#SCRIPT INPUT: $1 - INPUT - REQUIRED, $2 - JWT - REQUIRED, $3 - OUTPUT - OPTIONAL
#sh create-product.sh '<INPUT>' '<JWT>' '<OUTPUT>'

query='{ "query": "mutation { createProduct(input: {'$1'}) { '

if [ -n "$3" ]
  then
    query+=''$3' } '
  else
    query+=' id, name, category, price } '  #SET DEFAULT VALUE
fi;

query+=' }" '
query+=' } '

echo "$query"

curl -X POST \
-H "Content-Type: application/json" \
-H "Authorization: Bearer $2" \
-d "$query" \
-k https://127.0.0.1/e-shop/api/v1/catalog/graphql
