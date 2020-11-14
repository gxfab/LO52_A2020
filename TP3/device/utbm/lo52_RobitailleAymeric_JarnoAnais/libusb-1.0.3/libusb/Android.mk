# Define the path of the current directory, containing the Android.mk file itself
LOCAL_PATH := $(call my-dir)

# Clears previously defined variables to avoid collisions
include $(CLEAR_VARS)
$(info "Loading libusb Android.mk")

LOCAL_HEADER_LIBRARIES += libhardware_headers

# Define the name of the module to build
LOCAL_MODULE := libusb_1_0_3.$(TARGET_BOARD_PLATFORM)
LOCAL_MODULE_RELATIVE_PATH := hw
LOCAL_MODULE_CLASS := SHARED_LIBRARIES
LOCAL_MODULE_TAGS := optional

$(info $(LOCAL_MODULE))

# Specify a list of paths to include when compiling all sources
LOCAL_C_INCLUDES += $(LOCAL_PATH) $(LOCAL_PATH)/os

# Enumerate the source files
LOCAL_SRC_FILES := core.c descriptor.c io.c sync.c os/darwin_usb.c cos/linux_usbfs.c

# Collect all the information about the module from the LOCAL_XXX variables
# And determine how to build it
include $(BUILD_SHARED_LIBRARY)
