mkdir ~/toolchain
cd ~/toolchain
wget https://releases.linaro.org/components/toolchain/binaries/latest-7/aarch64-linux-gnu/gcc-linaro-7.5.0-2019.12-x86_64_aarch64-linux-gnu.tar.xz
tar -xf gcc-linaro-7.5.0-2019.12-x86_64_aarch64-linux-gnu.tar.xz

mkdir ~/android-kernel && cd ~/android-kernel
repo init -u https://android.googlesource.com/kernel/manifest -b hikey-linaro-android-4.19
repo sync -j8

export CROSS_COMPILE=~/toolchain/gcc-linaro-7.5.0-2019.12-x86_64_aarch64-linux-gnu/bin/aarch64-linux-gnu-
export ARCH=arm64

cd hikey-linaro/arch/mips/configs/generic/board-ranchu.config arch/arm64/configs/

make mrproper

make hikey_defconfig
make firstconfig board-ranchu.config
make savedefconfig
mv defconfig arch/arm64/configs/ranchu_defconfig
