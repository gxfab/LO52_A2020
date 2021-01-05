#include <jni.h>
#include <string>
#include <map>
#include <sstream>


extern "C" JNIEXPORT jstring JNICALL
Java_com_mguichar_viertel_1tp_MainActivity_write(JNIEnv *env, jobject, jint a){
    std::stringstream s;
    s << "READ: " << a << " * " << a  << " * " << a << " = " << a*a*a;

    return env->NewStringUTF(s.str().c_str());
}


extern "C" JNIEXPORT jstring JNICALL
Java_com_mguichar_viertel_1tp_MainActivity_read(JNIEnv* env, jobject, jint a){
    std::stringstream s;
    s << "READ: " << a << " * " << a << " = " << a*a;

    return env->NewStringUTF(s.str().c_str());
}


extern "C" JNIEXPORT jstring JNICALL
Java_com_mguichar_viertel_1tp_MainActivity_giveMeOneDirection(JNIEnv* env, jobject, jstring oneDirection){
    static const std::map<std::string, std::string> raus_raus = {
            {"LEFT", "LINKS"},
            {"RIGHT", "RECHTS"},
            {"UP", "VOR"},
            {"DOWN", "UNTEN"}
    };

    const char *str = env->GetStringUTFChars(oneDirection, nullptr);

    std::string youhou = raus_raus.at(str);
    return env->NewStringUTF(youhou.c_str());
}
