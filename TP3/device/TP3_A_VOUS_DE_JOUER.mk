$(call inherit-product, device/Linaro/hikey.mk)

PRODUCT_PACKAGES += libusb  

PRODUCT_PROPERTY_OVERRIDES := \
    ro.hw=lo_52 \
    net.dns1=8.8.8.8 \
    net.dns2=4.4.4.4

DEVICE_PACKAGE_OVERLAYS := overlay

PRODUCT_BRAND := UTBM_LO52
PRODUCT_NAME := lo52_KacimiAbdelhamid
PRODUCT_DEVICE := lo52_KacimiAbdelhamid
