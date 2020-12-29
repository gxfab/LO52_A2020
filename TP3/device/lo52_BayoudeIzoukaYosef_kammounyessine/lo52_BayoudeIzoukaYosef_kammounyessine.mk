# On hérite du produit hikey de Linaro pour le makefile
$(call inherit-product, device/linaro/hikey.mk)

# On a joute la libusb
PRODUCT_PACKAGES += libusb

# On ajoute les propriétés demandées dans le projet
PRODUCT_PROPERTY_OVERRIDES += ro.hw=lo52 \
net.dns1=8.8.8.8 \
net.dns2=4.4.4.4

# On définie le dossier où mettre les fichiers d'overlay
PRODUCT_PACKAGE_OVERLAYS := device/lo52_BayoudeIzoukaYosef_kammounyessine/overlay

# On choisis les noms du produit
PRODUCT_NAME := lo52_BayoudeIzoukaYosef_kammounyessine
PRODUCT_DEVICE := lo52_BayoudeIzoukaYosef_kammounyessine
PRODUCT_BRAND := lo52_BayoudeIzoukaYosef_kammounyessine
PRODUCT_MODEL := lo52_BayoudeIzoukaYosef_kammounyessine

include $(call all-subdir-makefiles)