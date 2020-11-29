repo init
repo init -u https://android.googlesource.com/kernel/manifest -b hikey-linaro-android-4.19 
repo sync
mkdir ~/toolchain
cd ~/toolchain
wget httŝ://releases.linaro.org/coponents/toolchain/binaries/latest-7/aarch64-linux-gnu/gcc-linaro-7.5.0-2019.12-x86_64_aarch64-linux-gnu.tar.xz
wget https://releases.linaro.org/coponents/toolchain/binaries/latest-7/aarch64-linux-gnu/gcc-linaro-7.5.0-2019.12-x86_64_aarch64-linux-gnu.tar.xz
wget https://releases.linaro.org/components/toolchain/binaries/latest-7/aarch64-linux-gnu/gcc-linaro-7.5.0-2019.12-x86_64_aarch64-linux-gnu.tar.xz
tar -xf gcc-linaro-7.5.0-2019.12-x86_64_aarch64-linux-gnu.tar.xz
cd android-kernel/hikey-linaro
export CROSS_COMPILE=~/toolchain/gcc-linaro-7.5.0-2019.12-x86_64_aarch64-linux-gnu/bin/aarch64-linux-gnu-
export ARCH=arm6
mkdir ../../configurations
cp defconfig ../configurations/defconfig_first_version
make hikey_defconfig
make clean
make board-ranchu.config
make savedefconfig
kdiff3 defconfig ../configurations/defconfig_first_version
