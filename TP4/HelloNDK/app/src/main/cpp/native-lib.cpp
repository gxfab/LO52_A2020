#include <jni.h>
#include <string>
#include <map>
#include <sstream>

extern "C" JNIEXPORT jstring JNICALL
Java_fr_utbm_hellondk_MainActivity_write(
        JNIEnv* env,
        jobject, jint a)  {
    std::stringstream ss;
    ss << "READ: " << a << "*" << a  << "*" << a << "=" << a*a*a;
    return env->NewStringUTF(ss.str().c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_fr_utbm_hellondk_MainActivity_read(
        JNIEnv* env,
        jobject, jint a)  {
    std::stringstream ss;
    ss << "READ: " << a << "*" << a << "=" << a*a;
    return env->NewStringUTF(ss.str().c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_fr_utbm_hellondk_MainActivity_directionInJapanese(
        JNIEnv* env,
        jobject, jstring direction)  {

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