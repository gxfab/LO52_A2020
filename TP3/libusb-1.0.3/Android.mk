# Copyright 2005 The Android Open Source Project
#
# Android.mk for libusb
#

commonSources := \
	core.c \
	descriptor.c \
	io.c \
	sync.c \
	os/darwin_usb.c \
	os/linux_usbfs.c
	
# For the host
LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_SRC_FILES := \
	$(commonSources)

LOCAL_C_INCLUDES += $(LOCAL_PATH) $(LOCAL_PATH)/os
LOCAL_MODULES := libusb

include $(BUILD_HOST_STATIC_LIBRARY)

#For the device
include $(CLEAR_VARS)

LOCAL_SRC_FILES := \
	$(commonSources)

LOCAL_MODULE := libusb
LOCAL_MODULE_TAGS := optional

include $(BUILD_SHARED_LIBRARY)