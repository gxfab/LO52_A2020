#include <jni.h>
#include <string>
#include <sstream>

extern "C" JNIEXPORT jstring JNICALL
Java_com_utbm_marie_ndktp4_1Marie_MainActivity_translateJaponais(JNIEnv *env, jobject, jstring direction) {
    const char *str = env->GetStringUTFChars(direction, nullptr);
    std::string result;
    if(strcmp(str, "LEFT")==0) {
        result = "左";
    }
    else if(strcmp(str, "RIGHT")==0) {
        result = "右";
    }
    else if(strcmp(str, "UP")==0) {
        result = "を";
    }
    else if(strcmp(str, "DOWN")==0) {
        result = "下";
    }
    else {
        result = "エラー";
    }
    std::string mydirection = str;
    result = "Translation: " + mydirection + " => " + result;
    return env->NewStringUTF(result.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_utbm_marie_ndktp4_1Marie_MainActivity_write(JNIEnv *env, jobject, jint a) {
    std::stringstream ss;
    if(a < 0 || a > 10){
        ss << "ERROR: the number entered must be between 0 and 10";
    }
    else{
        ss << "READ: " << a << "*" << a  << "*" << a << "=" << a*a*a;
    }

    return env->NewStringUTF(ss.str().c_str());
}





extern "C" JNIEXPORT jstring JNICALL
Java_com_utbm_marie_ndktp4_1Marie_MainActivity_read(JNIEnv *env, jobject thiz, jint a) {
    std::stringstream ss;

    if(a < 0 || a > 10){
        ss << "ERROR: the number entered must be between 0 and 10";
    }
    else{
        ss << "READ: " << a << "*" << a << "=" << a*a;
    }

    return env->NewStringUTF(ss.str().c_str());
}