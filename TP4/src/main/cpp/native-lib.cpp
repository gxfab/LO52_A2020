#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_tp4_MainActivity_DirectionFunction(
        JNIEnv* env, jobject /* this */, jstring buttonName) {

    const char *nativeString = env->GetStringUTFChars(buttonName, 0);
    if (strcmp(nativeString, "Up") == 0){
        return env->NewStringUTF("アップ");
    }
    if (strcmp(nativeString, "Down") == 0){
        return env->NewStringUTF("ダウン");
    }
    if (strcmp(nativeString, "Left") == 0){
        return env->NewStringUTF("左");
    }
    if (strcmp(nativeString, "Right") == 0){
        return env->NewStringUTF("正しい");
    }

}

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_tp4_MainActivity_ReadFunction(
        JNIEnv* env, jobject /* this */, jint numberGenerated) {

    jint numberCalc = numberGenerated*numberGenerated;
    char buf[64];
    sprintf(buf, "READ : %d*%d = %d", numberGenerated,numberGenerated,numberCalc);
    return env->NewStringUTF(buf);
}


extern "C" JNIEXPORT jstring JNICALL
Java_com_example_tp4_MainActivity_WriteFunction(
        JNIEnv* env, jobject /* this */, jint numberGenerated) {

    jint numberCalc = numberGenerated*numberGenerated*numberGenerated;
    char buf[64];
    sprintf(buf, "READ: %d*%d*%d = %d", numberGenerated, numberGenerated, numberGenerated, numberCalc);
    return env->NewStringUTF(buf);
}
