#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_lo52_1tp4_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_lo52_1tp4_MainActivity_upInJapanese(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "アップ";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_lo52_1tp4_MainActivity_downInJapanese(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "ダウン";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_lo52_1tp4_MainActivity_leftInJapanese(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "左";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_lo52_1tp4_MainActivity_rightInJapanese(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "正しい";
    return env->NewStringUTF(hello.c_str());
}


extern "C" JNIEXPORT jstring JNICALL
Java_com_example_lo52_1tp4_MainActivity_read(
        JNIEnv* env,
        jobject /* this */,
        jint value) {
    std::string hello = std::to_string(value * value);
    return env->NewStringUTF(hello.c_str());
}


extern "C" JNIEXPORT jstring JNICALL
Java_com_example_lo52_1tp4_MainActivity_write(
        JNIEnv* env,
        jobject /* this */,
        jint value) {
    std::string hello = std::to_string(value * value * value);
    return env->NewStringUTF(hello.c_str());
}
