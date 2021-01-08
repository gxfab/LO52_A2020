LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_SRC_FILES := \
	core.c \
	descriptor.c \
	io.c \
	sync.c \
	os/darwin_usb \
	os/linux_usbfs.c
	
LOCAL_CFLAGS += $(TOOL_CFLAGS) -DTIMESPEC_TO_TIMEVAL -pthread
LOCAL_LDFLAGS := $(TOOL_LDFLAGS) -pthread

LOCAL_MODULE := libusb
LOCAL_MODULE_TAGS := optional
LOCAL_PRELINK_MAP := false
include $(BUILD_SHARED_LIBRARY)