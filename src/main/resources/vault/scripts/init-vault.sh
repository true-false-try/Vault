#!/bin/sh

set -ex

INIT_FILE="/vault/keys/vault.init"

if [ -f "${INIT_FILE}" ]; then
    echo "${INIT_FILE} exists. Vault already initialized."
else

    echo "Initializing Vault..."
    sleep 5
    vault operator init -key-shares=3 -key-threshold=2 | tee "${INIT_FILE}" > /dev/null

    # Store unseal keys to files
    COUNTER=1
    cat "${INIT_FILE}" | grep '^Unseal' | awk '{print $4}' | while read -r key; do
        echo "${key}" > "/vault/keys/key-${COUNTER}"
        COUNTER=$((COUNTER + 1))
    done

    # Store Root Token to file
    cat "${INIT_FILE}" | grep '^Initial Root Token' | awk '{print $4}' | tee "/vault/keys/root-token" > /dev/null
    echo "Vault setup complete."
fi

# Check if necessary files are present
if [ ! -s "/vault/keys/root-token" ] || [ ! -s "/vault/keys/key-1" ] || [ ! -s "/vault/keys/key-2" ]; then
    echo "Vault is initialized, but unseal keys or token are missing."
    exit 1
fi

echo "Unsealing Vault"
export VAULT_TOKEN=$(cat "/vault/keys/root-token")
vault operator unseal "$(cat "/vault/keys/key-1")"
vault operator unseal "$(cat "/vault/keys/key-2")"

vault status
