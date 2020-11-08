# Script file

# Pre-config

git config --global user.name = "User"
git config --global user.email = "user@mail.com" 

sudo apt-get update

sudo apt install python

sudo apt install gcc

sudo apt-get install bison
sudo apt-get install flex

# Installing repo and cloning repository

mkdir -p ~/.bin
PATH="${HOME}./bin:${PATH}"
curl https://storage.googleapis.com/git-repo-downloads/repo> ~/.bin/repo
chmod a+rx ~/.bin/repo

repo init -u https://android.googlesource.com/kernel/manifest -b hikey-linaro-android-4.19

repo sync

# Configure kernel for a ranchu64 card

cp ~/android-kernel/hikey-linaro/arch/mips/configs/generic/board-ranchu.config ~/android-kernel/hikey-linaro/arch/arm64/configs

export ARCH=arm64
