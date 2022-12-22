#!/bin/bash

# kill vault
kill -- $(cat vault.pid)

# kill keycloak
kill -- $(lsof -t -i :8080)

rm vault.log vault.pid
rm keycloak.pid keycloak.log

# clearing previous data, needed because keycloak persists dev server data in the below path
# rm -rf /opt/keycloak/data