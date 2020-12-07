#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_sidawylepy_myapplication_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_sidawylepy_myapplication_MainActivity_nativeRead(
        JNIEnv* env,
jobject /* this */, jint number) {

    int resNumber = number * number;
    std::string str = "READ : ";
    str = str + std::to_string(resNumber);

return  env->NewStringUTF(str.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_sidawylepy_myapplication_MainActivity_nativeWrite(
        JNIEnv* env,
        jobject /* this */, jint number) {

    int resNumber = number * number * number;
    std::string str = "READ : ";
    str = str + std::to_string(resNumber);

    return  env->NewStringUTF(str.c_str());
}


extern "C" JNIEXPORT jstring JNICALL
Java_com_sidawylepy_myapplication_MainActivity_nativeJapaneseButton(
        JNIEnv* env,
        jobject /* this */, jstring buttonName) {

    const char* nativeButtonName = env->GetStringUTFChars(buttonName, 0);

    jstring res = env->NewStringUTF("-");

    if(strcmp(nativeButtonName,"Left") == 0)
    {
        res = env->NewStringUTF("左");
    }
    else if(strcmp(nativeButtonName,"Right") == 0)
    {
        res = env->NewStringUTF("右");
    }
    else if(strcmp(nativeButtonName,"Up") == 0)
    {
        res = env->NewStringUTF("上");
    }
    else if(strcmp(nativeButtonName,"Down") == 0)
    {
        res = env->NewStringUTF("下");
    }

    return res;
}
