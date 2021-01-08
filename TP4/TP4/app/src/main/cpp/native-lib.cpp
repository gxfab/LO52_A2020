#include <jni.h>
#include <string>
#include <sstream>
#include <iostream>

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_tp4_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_tp4_MainActivity_dispDirection(JNIEnv *env, jobject thiz, jstring button_name) {
    // TODO: implement dispDirection()
    const char *nativeString = env->GetStringUTFChars(button_name, 0);
    if (strcmp(nativeString, "Up") == 0){return env->NewStringUTF("アップ");}
    if (strcmp(nativeString, "Down") == 0){return env->NewStringUTF("ダウン");}
    if (strcmp(nativeString, "Left") == 0){return env->NewStringUTF("左");}
    if (strcmp(nativeString, "Right") == 0){return env->NewStringUTF("正しい");}

}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_tp4_MainActivity_dispWandR(JNIEnv *env, jobject thiz, jstring button_name , jint number) {
    // TODO: implement dispWandR()
    const char *nativeString = env->GetStringUTFChars(button_name, 0);
    if (strcmp(nativeString, "Read") == 0){
        int retnb = number*number;
        std::stringstream ssnb,ssret;
        ssnb << number;
        std::string nbString = ssnb.str();
        ssret << retnb;
        std::string retnbString = ssret.str();
        std::string msg  = "Read :" + nbString + "=>" + retnbString;
        char retmsg[100] ;
        strcpy(retmsg, msg.c_str());
        return env->NewStringUTF(retmsg);
    }
    if (strcmp(nativeString, "Write") == 0){
        int retnb = number*number*number;
        std::stringstream ssnb,ssret;
        ssnb << number;
        std::string nbString = ssnb.str();
        ssret << retnb;
        std::string retnbString = ssret.str();
        std::string msg  = "Write :" + nbString + "=>" + retnbString;
        char retmsg[100] ;
        strcpy(retmsg, msg.c_str());
        return env->NewStringUTF(retmsg);
    }

}


