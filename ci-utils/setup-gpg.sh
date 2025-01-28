#!/bin/bash

# references:
# https://stackoverflow.com/questions/70534089/is-it-possible-to-export-a-gpg-private-key-without-passphrase-being-provided-in
# https://gist.github.com/Dpbm/d5c2bf041589e01a409c701a17cef71e
# https://gist.github.com/sualeh/ae78dc16123899d7942bc38baba5203c

set -e

RESET_COLOR='\033[0m'
GREEN='\033[0;32m'


# GPG_DIR="$HOME/.gnupg" is set on the workflow
# GPG_SECRING_FILE_PATH="$GPG_DIR/secring.gpg" is set on the workflow

GPG_CONF_PATH="$GPG_DIR/gpg.conf"
GPG_CONF_AGENT_PATH="$GPG_DIR/gpg-agent.conf"

echo -e "$GREEN installing dependencies...$RESET_COLOR"
sudo apt-get update && sudo apt-get install -y gnupg

echo -e "$GREEN setup directories and files...$RESET_COLOR"
mkdir -p $GPG_DIR
chmod 700 $GPG_DIR
if [ ! -f $GPG_CONF_PATH ] 
then 
    echo -e "$GREEN creating $GPG_DIR...$RESET_COLOR"
    touch $GPG_CONF_PATH 
fi

if [ ! -f $GPG_CONF_AGENT_PATH ]
then 
    echo -e "$GREEN creating $GPG_CONF_AGENT_PATH...$RESET_COLOR"
    touch $GPG_CONF_AGENT_PATH 
fi

echo "use-agent" >> $GPG_CONF_PATH
echo "pinentry-mode loopback" >> $GPG_CONF_PATH
echo "allow-loopback-pinentry" >> $GPG_CONF_AGENT_PATH

echo -e "$GREEN reload agent...$RESET_COLOR"
echo RELOADAGENT | gpg-connect-agent

echo -e "$GREEN import private key...$RESET_COLOR"
cat <(echo -e "$GPG_KEY") | gpg --batch --import

echo -e "$GREEN export private key to path...$RESET_COLOR"
gpg --batch --pinentry-mode=loopback --yes --passphrase "$GPG_PASSWORD" --export-secret-keys --armor --output $GPG_SECRING_FILE_PATH