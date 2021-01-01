#Hérite de hikey-linaro 
$(call inherit-product, device/linaro/hikey/hikey.mk)

#Personnalisation du produit 
PRODUCT_PROPERTY_OVERRIDES := \
        ro.hw=lo52 \ 
        net.dns1=8.8.8.8 \
        net.dns2=4.4.4.4
        
#Spécifie le chemin des overlays
DEVICE_PACKAGE_OVERLAYS := device/lo52_GaujourBastien_PalermoEnzo/overlays

#Ajout de libusb aux packages du produit 
PRODUCT_PACKAGES += libusb-1.0.3

#Définition du produit 
PRODUCT_NAME := lo52_GaujourBastien_PalermoEnzo 
PRODUCT_DEVICE := ranchu
PRODUCT_BRAND := Android
PRODUCT_MODEL := Ranchu
PRODUCT_MANUFACTURER := Google
