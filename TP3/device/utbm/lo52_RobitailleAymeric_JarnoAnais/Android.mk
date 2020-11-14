# If we are building our device build libs

ifneq ($(filter lo52_RobitailleAymeric_JarnoAnais%, $(TARGET_DEVICE)),)
LOCAL_PATH := $(call my-dir)

# Include all sub Android.mk files in sub directories
include $(call all-makefiles-under,$(LOCAL_PATH))
endif
