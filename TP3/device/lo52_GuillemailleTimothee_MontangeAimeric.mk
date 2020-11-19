# Inherit from those products. Most specific first.
$(call inherit-product, device/linaro/hikey.mk)

PRODUCT_PACKAGES += \
        libusb_1_0_3.hikey

PRODUCT_PROPERTY_OVERRIDES := \
        ro.hw=lo52 \ 
        net.dns1=8.8.8.8 \
        net.dns2=4.4.4.4

DEVICE_PACKAGE_OVERLAYS := device/lo52_GuillemaileTimothee_MontangeAimeric/overlay

PRODUCT_NAME := lo52_GuillemaileTimothee_MontangeAimeric
PRODUCT_DEVICE := lo52_GuillemaileTimothee_MontangeAimeric
PRODUCT_BRAND := UTBM
PRODUCT_MODEL := LO52