LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
LOCAL_MODULE := libusb
LOCAL_SRC_FILES := core.c descriptor.c io.c sync.c os/linux_usbfs.c os/linux_usbfs.h  
include $(BUILD_SHARED_LIBRARY)
