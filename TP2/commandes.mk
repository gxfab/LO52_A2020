auto:
	mkdir android-kernel && cd android-kernel
	repo init -u https://android.googlesource.com/kernel/manifest -b hikey-linaro-android-4.14
	repo sync
	build/build.sh
	export TARGET_PREBUILT_KERNEL=DIST_DIR/Image.lz4-dtb
	adb reboot bootloader
	fastboot boot Image.lz4-dtb
	make ARCH=arm64range64-defconfig
	cd hikey-linaro
	make ARCH=arm64 CROSS_COMPILE=aarch64-linux-gnu

