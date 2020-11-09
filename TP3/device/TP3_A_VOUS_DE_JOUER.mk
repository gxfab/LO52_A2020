$(call inherit-product, device/linaro/hikey/hikey-common.mk)

PRODUCT_PACKAGES += libusb  

PRODUCT_PROPERTY_OVERRIDES := /
ro.hw=lo52 net.dns1 = 8.8.8.8 net.dns2 = 4.4.4.4

DEVICE_PACKAGE_OVERLAYS := device/utbm/Lo52_FelixDayet_MehdiFracso/overlay 
PRODUCT_NAME := Lo52_FelixDayet_MehdiFracso
PRODUCT_BRAND := Lo52_FelixDayet_MehdiFracso
PRODUCT_DEVICE := Lo52_FelixDayet_MehdiFracso
PRODUCT_MODEL := Lo52_FelixDayet_MehdiFracso
