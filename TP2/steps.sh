mkdir android-kernel && cd android-kernel
repo init -u https://android.googlesource.com/kernel/manifest -b hikey-linaro-android-4.14
repo sync

cd hikey-linaro
export PATH=$PATH:../prebuilts/gcc/linux-x86/aarch64/aarch64-linux-android-4.9/bin
export ARCH=arm64
export CROSS_COMPILE=aarch64-linux-android-

make clean
make mrproper

#copy board-ranchu.config to arch/arm64/configs/
cp arch/mips/configs/generic/board-ranchu.config arch/arm64/configs/

#load default config of hikey
make hikey_defconfig

#adding ranchu configuration
make oldconfig board-ranchu.config

#save merged configuration
make savedefconfig
mv defconfig arch/arm64/configs/ranchu_defconfig

make nconfig
