commonSources := \
core.c \
descriptor.c \
io.c \
sync.c \
os/darwin_usb.c \
os/linux_usbfs.c


BUILD_STATIC_LIBRARY := \
libusb-lib-static.a

BUILD_DYNAMIC_LIBRARY := \
libusb-lib.so


# For the host

LOCAL_PATH:= $(call my-dir)
include $(CLEAR_VARS)

LOCAL_SRC_FILES:= \
	$( commonSources )

LOCAL_MODULE:= libusb

include $(BUILD_STATIC_LIBRARY)



# For the device

include $(CLEAR_VARS)

LOCAL_SRC_FILES:= \
	$( commonSources )

LOCAL_MODULE:= libusb
LOCAL_MODULE_TAGS:= optional

include $(BUILD_DYNAMIC_LIBRARY)