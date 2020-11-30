#include <jni.h>
#include <string>
#include <map>
#include <sstream>
extern "C" JNIEXPORT jstring JNICALL
Java_com_example_ndk_MainActivity_write( JNIEnv* env, jobject, jint a)
{
    std::stringstream stdstring;
    stdstring << "READ: " << a << "*" << a  << "*" << a << "=" << a*a*a;
    return env->NewStringUTF(stdstring.str().c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_ndk_MainActivity_read(JNIEnv* env, jobject, jint a)
{
    std::stringstream stdstring;
    stdstring << "READ: " << a << "*" << a << "=" << a*a;
    return env->NewStringUTF(stdstring.str().c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_ndk_MainActivity_direction(JNIEnv* env, jobject, jstring direction)
{
    static const std::map<std::string, std::string> direction_translation = {
            {"LEFT", "左"},
            {"RIGHT", "右"},
            {"UP", "上"},
            {"DOWN", "下"}
    };
    const char *str = env->GetStringUTFChars(direction, nullptr);
    std::string str_jap = direction_translation.at(str);
    return env->NewStringUTF(str_jap.c_str());
}