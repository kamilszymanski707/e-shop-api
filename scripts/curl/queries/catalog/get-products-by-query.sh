#INPUT:
#id       - OPTIONAL
#name     - OPTIONAL
#category - OPTIONAL
#price    - OPTIONAL

#OUTPUT:
#id       - OPTIONAL
#name     - OPTIONAL
#category - OPTIONAL
#price    - OPTIONAL

#EXAMPLE:
#SCRIPT INPUT: $1 - INPUT - OPTIONAL, $2 - OUTPUT - OPTIONAL
#sh get-products-by-query.sh '<INPUT>' '<OUTPUT>'

query='{ "query": "query { getProductsByQuery'

#INPUT - OPTIONAL
if [ -n "$1" ]
  then
    query+='(input: {'$1'}) { '
  else
    query+=' { '
fi;

#OUTPUT - OPTIONAL
if [ -n "$2" ]
  then
    query+=''$2' } '
  else
    query+=' id, name, category, price } '  #SET DEFAULT VALUE
fi;

query+=' }" '
query+=' } '

echo "$query"

curl -X POST \
-H "Content-Type: application/json" \
-d "$query" \
-k https://127.0.0.1/e-shop/api/v1/catalog/graphql
