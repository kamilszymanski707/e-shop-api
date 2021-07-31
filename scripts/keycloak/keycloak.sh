docker run -d \
  -p 8080:8080 \
  -v ~/IdeaProjects/e-shop/e-shop-api/scripts/keycloak/tmp/realm-export.json:/tmp/realm-export.json \
  -e KEYCLOAK_USER=admin \
  -e KEYCLOAK_PASSWORD=admin \
  -e KEYCLOAK_IMPORT="/tmp/realm-export.json -Dkeycloak.profile.feature.upload_scripts=enabled" \
  wizzn/keycloak:12