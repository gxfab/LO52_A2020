# Definition de la version minimale de CMake requise
cmake_minimum_required(VERSION 3.4.1)

# Specification de notre library écrite en c++
add_library( # Nom de notre library.
             native-lib

             # Définition en tant que library partagée.
             SHARED

             # Chemin relatif de notre fichier source
             src/main/cpp/native-lib.cpp )

find_library( # Nom de la variable qui
              #contient le chemin de la librairie
              log-lib

              # Nom de la librairie NDK
              log )

# Liaison de notre libraie native au Log
target_link_libraries( # Notre librairie
                       native-lib

                       # Librairie log
                       ${log-lib} )