$(call inherit-product, device/linaro/hikey/hikey.mk)

PRODUCT_PACKAGES += \
        libusb_1_0_3.hikey

PRODUCT_PROPERTY_OVERRIDES := \
        ro.hw=lo52 \
        net.dns1=8.8.8.8 \
        net.dns2=4.4.4.4

PRODUCT_NAME != lo52_guichard
PRODUCT_DEVICE := lo52_guichard
PRODUCT_BRAND := UTBM
PRODUCT_MODEL := LO52
PRODUCT_MANUFACTURER := utbm
