
LOCAL_PATH := $(call my-dir)

LOCAL_MODULE := libusb

LOCAL_C_INCLUDES += $(LOCAL_PATH) $(LOCAL_PATH)/os
LOCAL_SRC_FILES := core.c descriptor.c io.c sync.c os/darwin_usb.c cos/linux_usbfs.c

include $(BUILD_SHARED_LIBRARY)
