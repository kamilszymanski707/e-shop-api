#INPUT:
#id
#name
#category
#price

#OUTPUT:
#id
#name
#category
#price

#EXAMPLE
#sh update-product.sh 'id: \"61046a9037573c25beaf8ad4\", name: \"XBOX ONE\", category: ELECTRONICS, price: 1200' 'eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJLaFF0RE1vU3R1N3V0UklTU3drT09tVXR5azY3Mnl3RHNhU2ZYcVpiOHI4In0.eyJleHAiOjE2Mjc3NTU1MTMsImlhdCI6MTYyNzc1MzcxNCwiYXV0aF90aW1lIjoxNjI3NzUzNzE0LCJqdGkiOiI5OGYyOWYwNS03Y2IwLTRkNGUtYWRiOC0yMjUwNjZkZjlkMmIiLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvYXV0aC9yZWFsbXMvZS1zaG9wIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjQ0MjA3ZjEwLWI3ZWYtNDA0ZC04Mjc2LWUzYTdkNDY5Yjc0YiIsInR5cCI6IkJlYXJlciIsImF6cCI6ImUtc2hvcCIsInNlc3Npb25fc3RhdGUiOiIxYzM4ZjMyNC1kZWNkLTQyNTQtOTZiZi1lNWQ1N2U4ZWMxOTgiLCJhY3IiOiIxIiwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImRlZmF1bHQtcm9sZXMtZS1zaG9wIiwib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImUtc2hvcCI6eyJyb2xlcyI6WyJhZG1pbiIsInVzZXIiXX0sImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoib3BlbmlkIGVtYWlsIHByb2ZpbGUiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsInByZWZlcnJlZF91c2VybmFtZSI6ImFkbWluIn0.g44HUdfvw3gURJUjzoIE38wxeQiB2AOIIcnoWqqquMLaN9_nQp1Ut0MRIesQ7JfhN2jm86t1usWJ-yOFA9aSY43KQsCGvjMnhCy2hNu1A0TLmwxseCy2EryP_f_a1rdMWXIq_zF_SgXu2Wt-uf0N5HcIZI2p9HSYndSpTThTWM8QUVGlt8jge2fPIZvZDw9MKWkKPxmxOezWzT_fm4vpGcUkulwuK3gk6cxakZWekXWbUSjutnL2dKsllO1By-yrvP7koI5n-LI5w8ZENEod6XDqlxMcDUMEQY-HFi7lBOSlFGBXUazCAAGPzP7ODc7VqkwbSKna3Om2Jin3MXNQcw' 'id, name, category, price'

query='{ "query": "mutation { updateProduct(input: {'"$1"'}) { '

#OUTPUT - OPTIONAL
if [ -n "$3" ]
  then
    query+=''"$3"' } '
  else
    query+=' id, name, category, price } '
fi;

query+=' }" '
query+=' } '

echo "$query"

curl -X POST \
-H "Content-Type: application/json" \
-H "Authorization: Bearer $2" \
-d "$query" \
-k https://127.0.0.1/e-shop/api/v1/catalog/graphql
