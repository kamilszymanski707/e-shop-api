#INPUT:
#id           - OPTIONAL
#description  - OPTIONAL
#productId    - OPTIONAL

#OUTPUT:
#id           - OPTIONAL
#description  - OPTIONAL
#productId    - OPTIONAL
#amount       - OPTIONAL

#EXAMPLE:
#SCRIPT INPUT: $1 - INPUT - OPTIONAL, $2 - OUTPUT - OPTIONAL
#sh get-coupons-by-query.sh '<INPUT>' '<OUTPUT>'

query='{ "query": "query { getCouponsByQuery'

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
    query+=' id, description, productId, amount } '  #SET DEFAULT VALUE
fi;

query+=' }" '
query+=' } '

echo "$query"

curl -X POST \
-H "Content-Type: application/json" \
-d "$query" \
-k https://127.0.0.1/e-shop/api/v1/discount/graphql
