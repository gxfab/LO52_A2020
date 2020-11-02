LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
include $(BUILD_SHARED_LIBRARY)


LOCAL_MODULE:=libusb
LOCAL_SRC_FILES:= \
	 core.c \
	 descriptor.c \
	 io.c \
	 sync.c \
	 os/linux_usbfs.c 

LOCAL_C_INCLUDES +=	/external/libusb/libusb.h \
					/external/libusb/libusbi.h \
					/external/libusb/os/linux_usbfs.h 


LOCAL_SHARED_LIBRARIES := libc libusb
LOCAL_MODULE_TAGS:= optional

$(call inherit-product, device/Linaro/hikey.mk)
PRODUCT_NAME := lo52_AbbeMagsen_PereiraMaxime
PRODUCT_DEVICE := lo52_AbbeMagsen_PereiraMaxime
PRODUCT_PROPERTY_OVERRIDES := \
    ro.hw=lo_52 \
    net.dns1=8.8.8.8 \
    net.dns2=4.4.4.4
PRODUCT_PACKAGES+= libusb
PRODUCT_PACKAGE_OVERLAYS := ../../device/overlay/