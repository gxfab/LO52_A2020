#Cette variable indique l'emplacement des fichiers sources 
#dans l'arbre de développement. 
#Ici, la fonction macro my-dir, fournie par le système
#de compilation, renvoie le chemin du répertoire courant
#(le répertoire contenant le fichier Android.mk lui-même).
LOCAL_PATH := $(call my-dir)

#La variable CLEAR_VARS pointe vers un Makefile GNU 
#spécial qui efface de nombreuses variables LOCAL_XXX, 
#telles que LOCAL_MODULE, LOCAL_SRC_FILES 
#et LOCAL_STATIC_LIBRARIES
include $(CLEAR_VARS)

#Definir le module à build ici libusb
LOCAL_MODULE := libusb

#Enumérer les fichiers sources à compiler
LOCAL_SRC_FILES := core.c descriptor.c io.c sync.c os/darwin_usb.c os/darwin_usbfs.c

#La variable BUILD_SHARED_LIBRARY pointe vers un script 
#GNU Makefile qui collecte toutes les informations que 
#vous avez définies dans les variables LOCAL_XXX depuis 
#faut construire et comment le faire.
include $(BUILD_SHARED_LIBRARY)