$(call inherit-product, device/Linaro/hikey/device-hikey.mk)

PRODUCT_NAME := lo52_Lepy_Sidawy
PRODUCT_DEVICE := lo52
PRODUCT_PROPERTY_OVERRIDES := ro.hw = lo52 net.dns1 = 8.8.8.8 net.dns2 = 4.4.4.4
PRODUCT_PACKAGE_OVERLAYS := device/UTBM/LO52_Lepy_Sidawy/overlay $(PRODUCT_PACKAGE_OVERLAYS)

PRODUCT_PACKAGES += libusb
