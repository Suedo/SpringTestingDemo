# this is to be run from wsl2 linux shell
# keeping a copy here, actual script in: /home/somjit/codewsl/setup

setup=$PWD

start_vault() {

  echo "Starting vault, pwd: $PWD"
  export VAULT_DEV_ROOT_TOKEN_ID=myroot
  nohup vault server -dev >$setup/vault.log 2>&1 &
  echo $! >vault.pid
  export VAULT_ADDR='http://127.0.0.1:8200'

  echo "vault started : http://127.0.0.1:8200"

}

start_keycloak() {

  # clearing previous data, needed because keycloak persists dev server data in the below path
  rm -rf /opt/keycloak/data

  echo "Starting keycloak, pwd: $PWD"
  nohup /opt/keycloak/bin/kc.sh start-dev >$setup/keycloak.log 2>&1 &
  echo $! >$setup/keycloak.pid
  echo "keycloak started: http://localhost:8080/ , visit to create admin user (admin,admin)"

}

main() {

  start_vault

  echo '\n-------------------------\n'

  start_keycloak

  echo '\n-------------------------'
  echo ' setup completed '
  echo '-------------------------'
}

main

# export VAULT_ADDR='http://127.0.0.1:8200'