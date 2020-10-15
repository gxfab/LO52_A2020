#/bin/bash

# Sortie en cas d'erreur 
set -e

# Téléchargement de la toolchain pour processeur cible : 64-bit Armv8 Cortex-A, little-endian
# Cette toolchain n'est pas utile à la configuration mais le devient pour la création des images kernel
mkdir ~/toolchain
cd ~/toolchain
wget https://releases.linaro.org/components/toolchain/binaries/latest-7/aarch64-linux-gnu/gcc-linaro-7.5.0-2019.12-x86_64_aarch64-linux-gnu.tar.xz
tar -xf gcc-linaro-7.5.0-2019.12-x86_64_aarch64-linux-gnu.tar.xz

# Création du dossier de travail `android-kernel`
mkdir ~/android-kernel && cd ~/android-kernel

# Initialisation avec repo et la branche `hikey-linaro-android-4.19`
repo init -u https://android.googlesource.com/kernel/manifest -b hikey-linaro-android-4.19

# Téléchargement des sources
repo sync -j8

# Définition de l'architecture utilisée et du cross-compiler
export CROSS_COMPILE=~/toolchain/gcc-linaro-7.5.0-2019.12-x86_64_aarch64-linux-gnu/bin/aarch64-linux-gnu-
export ARCH=arm64

# On se place dans le dossier contenant le makefile
cd hikey-linaro

# Copie du fichier de configuration pour la carte ranchu dans arm64
cp arch/mips/configs/generic/board-ranchu.config arch/arm64/configs/

# On nettoie les sorties de conmpilation et configurations chargés
make clean
make mrproper

# Chargement de la configuration de base pour hikey
make hikey_defconfig

# Ajout de la configuration de la carte ranchu
make oldconfig board-ranchu.config

# Sauvegarde de la fusion des deux configurations
make savedefconfig
mv defconfig arch/arm64/configs/ranchu_defconfig

# Affichage de la sortie
if [ $? -eq 0 ]; then
    echo "Success"
else
    echo "Configuration failed"
fi