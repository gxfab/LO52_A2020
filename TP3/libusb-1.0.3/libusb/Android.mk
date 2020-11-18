LOCAL_PATH:= $(call my-dir)
	include $( CLEAR_VARS )
	# nom du module
	LOCAL_MODULE := libusb
	# .h
	LOCAL_C_INCLUDES += $( LOCAL_PATH )/ include $( LOCAL_PATH )/os
	# Sources à compiler
	LOCAL_SRC_FILES := core.c descriptor.c io.c sync.c
	include $( BUILD_SHARED_LIBRARY )
