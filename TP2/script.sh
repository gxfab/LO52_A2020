sudo apt-get install repo
mkdir ~/android-kernel  
cd ~/android-kernel
repo init  -u https://android.googlesource.com/kernel/manifest -b hikey-linaro-android-4.19
repo  sync
export CROSS_COMPILE=~/toolchain/gcc-linaro-7.5.0-2019.12-x86_64_aarch64-linux-gnu/bin/aarch64-linux-gnu-
export ARCH=arm64
cd ~/android-kernel/hikey-linaro
make defconfig
make savedefconfig
mkdir ~/config
cp defconfig ~/config/defconfig_default
make clean
cp arch/mips/configs/generic/board-ranchu.config arch/arm64/configs/
make board-ranchu.config
make savedefconfig
cp defconfig ~/config/defconfig_ranchu64