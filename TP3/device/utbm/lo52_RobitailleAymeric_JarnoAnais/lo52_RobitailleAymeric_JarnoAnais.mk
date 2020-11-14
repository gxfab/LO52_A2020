PRODUCT_PACKAGES += \
        libusb_1_0_3.hikey
# Note here this is the name of the board, so since our product runs on a hikey board we have let the hikey name
# but it can be change in the device config



PRODUCT_PROPERTY_OVERRIDES := \
        ro.hw=lo52 \ 
        net.dns1=8.8.8.8 \
        net.dns2=4.4.4.4


# Inherit from those products. Most specific first.
$(call inherit-product, device/linaro/hikey/hikey.mk)

DEVICE_PACKAGE_OVERLAYS := device/utbm/lo52_RobitailleAymeric_JarnoAnais/overlay

PRODUCT_NAME := lo52_RobitailleAymeric_JarnoAnais
PRODUCT_DEVICE := lo52_RobitailleAymeric_JarnoAnais
PRODUCT_BRAND := UTBM
PRODUCT_MODEL := LO52
PRODUCT_MANUFACTURER := utbm
