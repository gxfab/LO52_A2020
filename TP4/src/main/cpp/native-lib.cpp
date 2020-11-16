#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_tp4_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_tp4_MainActivity_displayDirection(
        JNIEnv* env, jobject /* this */, jstring buttonName) {

    const char *nativeString = env->GetStringUTFChars(buttonName, 0);
    if (strcmp(nativeString, "Up") == 0){return env->NewStringUTF("アップ");}
    if (strcmp(nativeString, "Down") == 0){return env->NewStringUTF("ダウン");}
    if (strcmp(nativeString, "Left") == 0){return env->NewStringUTF("左");}
    if (strcmp(nativeString, "Right") == 0){return env->NewStringUTF("正しい");}

}


extern "C" JNIEXPORT jstring JNICALL
Java_com_example_tp4_MainActivity_displayRead(
        JNIEnv* env, jobject /* this */, jint number) {

    jint finalNumber = number*number; //Multiplication demandée
    char buf[64]; // Création d'un char pour contenir le nombre calculé
    sprintf(buf, "%d", finalNumber);  // le char contient maintenant le nombre calculé
    char msg[60] = "READ : ";
    strcat(msg, buf); // Concaténation du message avec le nombre
    return env->NewStringUTF(msg); // On envoie
}


extern "C" JNIEXPORT jstring JNICALL
Java_com_example_tp4_MainActivity_displayWrite(
        JNIEnv* env, jobject /* this */, jint number) {

    jint finalNumber = number*number*number; //Multiplication demandée
    char buf[64]; // Création d'un char pour contenir le nombre calculé
    sprintf(buf, "%d", finalNumber);  // le char contient maintenant le nombre calculé
    char msg[60] = "READ : ";
    strcat(msg, buf); // Concaténation du message avec le nombre
    return env->NewStringUTF(msg); // On envoie
}
