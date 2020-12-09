$(call inherit-product, device/linaro/hikey/hikey.mk)
PRODUCT_PROPERTY_OVERRIDES := \
						ro.hw = lo52 \
						net.dns1 = 8.8.8.8 \
						net.dns2 = 4.4.4.4
DEVICE_PACKAGE_OVERLAYS := device/utbm/lo52_Manou_Abbaltu_Nouassi_Zechirine/overlay
PRODUCT_PACKAGES += libusb
PRODUCT_NAME := lo52_Manou_Abbaltu_Nouassi_Zechirine
PRODUCT_BRAND := lo52_Manou_Abbaltu_Nouassi_Zechirine
PRODUCT_DEVICE := lo52_Manou_Abbaltu_Nouassi_Zechirine
PRODUCT_MODEL := lo52_Manou_Abbaltu_Nouassi_Zechirine
