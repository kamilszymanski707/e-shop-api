#INPUT:
#id
#name
#category

#OUTPUT:
#id
#name
#category
#price

#EXAMPLE
#sh get-products-by-query.sh 'id, name, category, price' 'id: \"61046a9037573c25beaf8ad4\", category: AUTOMOTIVE, name: \"Audi Q3\"'

query='{ "query": "query { getProductsByQuery'

#INPUT - OPTIONAL
if [ -n "$2" ]
  then
    query+='(input: {'"$2"'}) { '
  else
    query+=' { '
fi;

#OUTPUT - OPTIONAL
if [ -n "$1" ]
  then
    query+=''"$1"' } '
  else
    query+=' id, name, category, price } '
fi;

query+=' }" '
query+=' } '

echo "$query"

curl -X POST \
-H "Content-Type: application/json" \
-d "$query" \
-k https://127.0.0.1/e-shop/api/v1/catalog/graphql
