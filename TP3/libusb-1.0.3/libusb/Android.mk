LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)
LOCAL_SRC_FILES:= core.c descriptor.c io.c sync.c os/linux_usbfs.c
LOCAL_C_INCLUDES += external/libusb-1.0.3/ external/libusb-1.0.3/libusb/ external/libusb-1.0.3/libusb/os
LOCAL_MODULE:= libusb
include $(BUILD_SHARED_LIBRARY)
