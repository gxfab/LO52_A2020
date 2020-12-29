
# Inherit from the common Open Source product configuration
$(call inherit-product, device/hikey/hikey.mk)

PRODUCT_PROPERTY_OVERRIDES := \
						ro.hw = lo52 \
						net.dns1 = 8.8.8.8 \
						net.dns2 = 4.4.4.4
DEVICE_PACKAGE_OVERLAYS := device/utbm/lo52_AgotsiGedeon/overlay

#libusb
PRODUCT_PACKAGES += libusb-1.0.3

#Product definition
PRODUCT_NAME := lo52_AgotsiGedeon
PRODUCT_DEVICE := lo52_AgotsiGedeon
PRODUCT_BRAND := UTBM
PRODUCT_MODEL := LO52
PRODUCT_MANUFACTURER := UTBM
