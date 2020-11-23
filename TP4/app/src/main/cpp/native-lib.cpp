#include <jni.h>
#include <string>
#include <math.h>
using namespace std;



extern "C"
JNIEXPORT jstring JNICALL
Java_com_dayetfracso_tp4application_MainActivity_calculateSquare(JNIEnv *env, jobject thiz, jint value) {
    int intValue = (int)value;
    return env->NewStringUTF(to_string(pow(intValue,2)).c_str());
}


extern "C"
JNIEXPORT jstring JNICALL
Java_com_dayetfracso_tp4application_MainActivity_calculateCube(JNIEnv *env, jobject thiz, jint value) {
    int intValue = (int)value;
    return env->NewStringUTF(to_string(pow(intValue,3)).c_str());
}


extern "C"
JNIEXPORT jstring JNICALL
Java_com_dayetfracso_tp4application_MainActivity_getJapaneseEquivalent(JNIEnv *env, jobject thiz,

                                                                       jint direction) {
    int value = (int) direction;

    string resultat;

    switch (value) {
        case 0:
            resultat = "Appu";
            break;
        case 1:
            resultat = "Daun";
            break;
        case 2:
            resultat = "Tadashii";
            break;
        case 3:
            resultat = "Hidari";
            break;
    }

    return env->NewStringUTF(resultat.c_str());

}