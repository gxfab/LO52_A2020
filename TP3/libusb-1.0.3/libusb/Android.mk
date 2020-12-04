# Copyright 2020 The Android Open Source Project
# By MANOU Abbaltu Auguste
#  &
#    NOUASSI Zechirine
#
# Android.mk for libusb
#

#set current directory as our LOCAL_PATH variable value
LOCAL_PATH := $(call my-dir)

#clear all env. variables
include $(CLEAR_VARS)

# Define all sources files in commonsSources variable
commonSources := \
	core.c \
	descriptor.c \
	io.c \
	sync.c \
	os/darwin_usb \
	os/linux_usbfs.c

# Setting LOCAL_SRC_FILES value from commonSources
LOCAL_SRC_FILES := \
	$( commonSources )

#Define module name
LOCAL_MODULE := libusb

#Define final build
include $(BUILD_SHARED_LIBRARY)

