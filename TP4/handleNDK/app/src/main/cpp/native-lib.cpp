#include <jni.h>
#include <string>
#include <map>
#include <sstream>

extern "C" JNIEXPORT jstring JNICALL
Java_fr_utbm_handlendk_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_fr_utbm_handlendk_MainActivity_write(
        JNIEnv* env,
        jobject, jint a)  {
    std::stringstream ss;
    ss << "READ: " << a << "*" << a  << "*" << a << "=" << a*a*a;
    return env->NewStringUTF(ss.str().c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_fr_utbm_handlendk_MainActivity_direction(JNIEnv *env, jobject thiz, jstring direction) {

    static const std::map<std::string, std::string> directionMap = {
            {"LEFT", "左"},
            {"RIGHT", "右"},
            {"UP", "上"},
            {"DOWN", "下"}
    };
    const char *str = env->GetStringUTFChars(direction, nullptr);
    std::string translated = directionMap.at(str);
    return env->NewStringUTF(translated.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_fr_utbm_handlendk_MainActivity_read(JNIEnv *env, jobject thiz, jint a) {
        std::stringstream ss;
        ss << "READ: " << a << "*" << a << "=" << a*a;
        return env->NewStringUTF(ss.str().c_str());
}