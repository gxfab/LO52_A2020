#include <jni.h>
#include <string>
#include <sstream>

extern "C" JNIEXPORT jstring JNICALL
Java_com_utbm_nouassi_manou_ndktp4_MainActivity_write(
        JNIEnv* env,
        jobject, jint a)  {
    std::stringstream ss;
    if(a < 0 || a > 10){
        ss << "ERREUR: le nombre saisi doit être compris entre 0 et 10";
    }
    else{
        ss << "READ: " << a << "*" << a  << "*" << a << "=" << a*a*a;
    }

    return env->NewStringUTF(ss.str().c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_utbm_nouassi_manou_ndktp4_MainActivity_read(
        JNIEnv* env,
        jobject, jint a)  {
    std::stringstream ss;

    if(a < 0 || a > 10){
        ss << "ERREUR: le nombre saisi doit être compris entre 0 et 10";
    }
    else{
        ss << "READ: " << a << "*" << a << "=" << a*a;
    }

    return env->NewStringUTF(ss.str().c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_utbm_nouassi_manou_ndktp4_MainActivity_translateJaponais(
        JNIEnv* env,
        jobject, jstring direction)  {

    const char *str = env->GetStringUTFChars(direction, nullptr);
    std::string resultat;
    if(strcmp(str, "LEFT")==0) {
        resultat = "左";
    }
    else if(strcmp(str, "RIGHT")==0) {
        resultat = "右";
    }
    else if(strcmp(str, "UP")==0) {
        resultat = "を";
    }
    else if(strcmp(str, "DOWN")==0) {
        resultat = "下";
    }
    else {
        resultat = "エラー";
    }
    std::string mydirection = str;
    resultat = "Traduction: " + mydirection + " => " + resultat;
    return env->NewStringUTF(resultat.c_str());
}