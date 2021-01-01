#!/bin/bash

# Création du dossier et on se place dedans
mkdir ~/android-kernel && cd ~/android-kernel

# Initialisation avec repo de la branche `hikey-linaro-android-4.19`
repo init -u https://android.googlesource.com/kernel/manifest -b hikey-linaro-android-4.19
repo sync

# On se place dans le dossier contenant le makefile
cd hikey-linaro

# Définition de l'architecture utilisée (Sinon le makefile cherche dans le dossier x86 par défaut)
export ARCH=arm64

# Copie du fichier de configuration pour la carte ranchu dans arm64
cp arch/mips/configs/generic/board-ranchu.config arch/arm64/configs/

# Chargement de la configuration de base pour hikey
make hikey_defconfig

# Ajout de la configuration pour carte ranchu
make oldconfig board-ranchu.config

# Save fusion de hikey_defconfig et board-ranchu
make savedefconfig
mv defconfig arch/arm64/configs/ranchu_defconfig
