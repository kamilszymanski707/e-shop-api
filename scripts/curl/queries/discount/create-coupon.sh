#INPUT:
#description  - REQUIRED
#productId    - REQUIRED
#amount       - REQUIRED

#OUTPUT:
#id           - OPTIONAL
#description  - OPTIONAL
#productId    - OPTIONAL
#amount       - OPTIONAL

#EXAMPLE:
#SCRIPT INPUT: $1 - INPUT - REQUIRED, $2 - JWT - REQUIRED, $3 - OUTPUT - OPTIONAL
#sh create-coupon.sh '<INPUT>' '<JWT>' '<OUTPUT>'

query='{ "query": "mutation { createCoupon(input: {'$1'}) { '

if [ -n "$3" ]
  then
    query+=''$3' } '
  else
    query+=' id, description, productId, amount } '  #SET DEFAULT VALUE
fi;

query+=' }" '
query+=' } '

echo "$query"

curl -X POST \
-H "Content-Type: application/json" \
-H "Authorization: Bearer $2" \
-d "$query" \
-k https://127.0.0.1/e-shop/api/v1/discount/graphql
