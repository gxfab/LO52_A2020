$(call inherit-product, device/Linaro/hikey.mk)
PRODUCT_PACKAGES+= libusb
PRODUCT_PROPERTY_OVERRIDES := \
    ro.hw=lo_52 \
    net.dns1=8.8.8.8 \
    net.dns2=4.4.4.4
PRODUCT_PACKAGE_OVERLAYS := overlay/
PRODUCT_NAME := lo52_GASSERTatiana_MARINLoic
PRODUCT_BRAND := lo52
PRODUCT_DEVICE := lo52_GASSERTatiana_MARINLoic
PRODUCT_MODEL := GASSERTatiana_MARINLoic