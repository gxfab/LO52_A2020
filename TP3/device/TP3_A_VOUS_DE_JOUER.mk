$(call inherit-product, device/linaro/hikey/hikey-common.mk)

PRODUCT_PACKAGES += libusb  

PRODUCT_PROPERTY_OVERRIDES := /
ro.hw=lo52 net.dns1 = 8.8.8.8 net.dns2 = 4.4.4.4

DEVICE_PACKAGE_OVERLAYS := device/utbm/lo52/overlay 
PRODUCT_NAME := lo52
PRODUCT_BRAND := lo52
PRODUCT_DEVICE := lo52
PRODUCT_MODEL := lo52