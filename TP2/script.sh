#!/bin/sh
mkdir android-kernel && cd android-kernel
python /home/elmehdibahbouhi/Desktop/lo52/LO52_A2020/TP2/repo init –u https://android.googlesource.com/kernel/manifest -b hikey-linaro-android-4.19
python /home/elmehdibahbouhi/Desktop/lo52/LO52_A2020/TP2/repo sync
make defconfig
diff build.config /home/elmehdibahbouhi/Desktop/lo52/LO52_A2020/TP2/android-kernel/hikey-linaro/arch/arm64/configs/hikey960_defconfig
make xconfig
make savedefconfig