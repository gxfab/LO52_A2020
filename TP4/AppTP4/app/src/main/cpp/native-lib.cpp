#include <jni.h>
#include <string.h>

extern "C" JNIEXPORT jstring JNICALL   //Récupère le bouton en paramètre et renvoi une string avec la direction correspondante
Java_com_tps_apptp4_MainActivity_callDirection(JNIEnv *env, jobject obj, jstring myButtonName)
{

    const char* nativeString = env->GetStringUTFChars(myButtonName, JNI_FALSE);    //Récupère la string passée en paramètre et la transforme dans une string compatible C++
    if (strcmp(nativeString, "UP") == 0) return env->NewStringUTF("アップ");         //Compare la string (avec strcmp) et renvoi le texte japonais correspondant
    if (strcmp(nativeString, "DOWN") == 0) return env->NewStringUTF("ダウン");
    if (strcmp(nativeString, "LEFT") == 0) return env->NewStringUTF("左");
    if (strcmp(nativeString, "RIGHT") == 0) return env->NewStringUTF("右");
    env->ReleaseStringUTFChars(myButtonName, nativeString);
}