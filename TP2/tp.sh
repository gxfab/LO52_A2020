#/bin/bash

mkdir android-kernel && cd android-kernel #création d'un dossier pour le tp
repo init -u https://android.googlesource.com/kernel/manifest -b hikey-linaro-android-4.19 # Initialisation du dossier avec repo et la branche hikey-linaro
repo sync  #DL

cp hikey-linaro/arch/mips/configs/generic/board-ranchu.config hikey-linaro/arch/arm64/configs/ #copie du fichier

make board-ranchu.config

repo diff