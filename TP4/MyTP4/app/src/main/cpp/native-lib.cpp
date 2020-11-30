#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_mytp4_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_mytp4_MainActivity_directionInJapanese(JNIEnv *env, jobject thiz, jstring buttonText) {
    // implementation of directionButton()
    std::string buttonValue = env->GetStringUTFChars(buttonText, 0);

    if (buttonValue == "UP")
    {
        return env->NewStringUTF("UP : アップ");
    }
    else if (buttonValue == "DOWN")
    {
        return env->NewStringUTF("DOWN : ダウン");
    }
    else if (buttonValue == "LEFT")
    {
        return env->NewStringUTF("LEFT : 左");
    }
    else if (buttonValue == "RIGHT")
    {
        return env->NewStringUTF("RIGHT : 正しい");
    }
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_mytp4_MainActivity_calculate(JNIEnv *env, jobject thiz, jint a, jboolean isREAD) {
    std::string s;
    if(isREAD)
        s = "READ : " + std::to_string(a) + "*" + std::to_string(a) + "=" + std::to_string(a*a);
    else
        s = "WRITE : " + std::to_string(a) + "*" + std::to_string(a) + "*" + std::to_string(a) + "=" + std::to_string(a*a*a);
    return env->NewStringUTF(s.c_str());
}