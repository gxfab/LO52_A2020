PRODUCT_PACKAGES += \
	libusb

PRODUCT_PROPERTY_OVERRIDES := \
	net.dns1 = 8.8.8.8 \
	net.dns2 = 4.4.4.4 \
	ro.hw = lo52


# Héritage .
$(call inherit-product, $(SRC_TARGET_DIR)/product/hikey_linaro.mk)

PRODUCT_NAME := lo52_info
PRODUCT_DEVICE := l052
PRODUCT_BRAND := Android
PRODUCT_MODEL := LO52