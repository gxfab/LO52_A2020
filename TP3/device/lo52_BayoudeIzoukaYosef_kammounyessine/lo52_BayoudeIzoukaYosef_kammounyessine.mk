# Heritage from the product hikey linaro
$(call inherit-product, device/linaro/hikey.mk)

# Append libusb
PRODUCT_PACKAGES += libusb

# customized proprety
PRODUCT_PROPERTY_OVERRIDES += ro.hw=lo52 \
net.dns1=8.8.8.8 \
net.dns2=4.4.4.4

# overlay directory
PRODUCT_PACKAGE_OVERLAYS := device/lo52_BayoudeIzoukaYosef_kammounyessine/overlay

# define the name of the product
PRODUCT_NAME := lo52_BayoudeIzoukaYosef_kammounyessine
PRODUCT_DEVICE := lo52_BayoudeIzoukaYosef_kammounyessine
PRODUCT_BRAND := lo52_BayoudeIzoukaYosef_kammounyessine
PRODUCT_MODEL := lo52_BayoudeIzoukaYosef_kammounyessine

include $(call all-subdir-makefiles)
