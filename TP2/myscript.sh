#!/bin/bash

# Ici on crée le répertoire de travail
mkdir ~/android-kernel && cd ~/android-kernel

# On fait l'initialisation en prenant la branche hikey-linaro-4.19
repo init -u https://android.googlesource.com/kernel/manifest -b hikey-linaro-android-4.19 --repo-url ~/Téléchargements/clone.bundle

# Utilisation de repo syc (on télécharge les fichiers sources)
repo sync

# Définition de l'architecture (ici ARM64)
export ARCH=arm64

# Positionnement dans le dossier hikey-linaro qui contient le Makefile
cd hikey-linaro

# On nettoie avec les commandes make clean et make mrproper
make clean
make mrproper

# On charge la configuration de base pour hikey puis on la sauvegarde dans un répertoire configs
make hikey_defconfig
cp .config configs/hikey_defconfig

# On copie la config de la carte ranchu dans le répertoire configs
cp arch/mips/configs/generic/board-ranchu.config arch/arm64/configs/

# On charge la configuration de la carte ranchu
make oldconfig board-ranchu.config
cp .config configs/ranchuconfig

# Finalement on opère une sauvegarde de la fusion des deux configs (et on la sauvegarde sous le nom lastconfig)
make savedefconfig
mv defconfig configs/lastconfig
