#$1 - PEM KEY   - gateway-key.pem
#$2 - PEM CERT  - gateway-cert.pem
#$3 - P12 CERT  - gateway-cert.p12
#$4 - ALIAS     - gateway

openssl req -x509 -newkey rsa:4096 -keyout "$1" -out "$2" -days 730 \
 && openssl pkcs12 -export -out "$3" -in "$2" -inkey "$1" -name "$4"
