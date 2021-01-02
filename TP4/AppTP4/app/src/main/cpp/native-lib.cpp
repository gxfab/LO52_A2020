#include <jni.h>
#include <string.h>
#include <iostream>  //For stoi()

extern "C" JNIEXPORT jstring JNICALL   //Récupère le bouton en paramètre et renvoi une string avec la direction correspondante
Java_com_tps_apptp4_MainActivity_callDirection(JNIEnv *env, jobject obj, jstring myButtonName)
{

    const char* nativeString = env->GetStringUTFChars(myButtonName, JNI_FALSE);    //Récupère la string passée en paramètre et la transforme dans une string compatible C++
    if (strcmp(nativeString, "UP") == 0) return env->NewStringUTF("アップ");         //Compare la string (avec strcmp) et renvoi le texte japonais correspondant
    if (strcmp(nativeString, "DOWN") == 0) return env->NewStringUTF("ダウン");
    if (strcmp(nativeString, "LEFT") == 0) return env->NewStringUTF("左");
    if (strcmp(nativeString, "RIGHT") == 0) return env->NewStringUTF("右");
    env->ReleaseStringUTFChars(myButtonName, nativeString);
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_tps_apptp4_MainActivity_callMultiplication(JNIEnv *env, jobject thiz, jint my_number, jstring my_button_name)
{
    //const char* nativeString2 = env->GetIntField(my_number, JNI_FALSE);
    if (my_number > 0 && my_number < 11)
    {
        //Récupère le button name et le converti en native string
        const char* nativeString = env->GetStringUTFChars(my_button_name, JNI_FALSE);

        //String qui contiendra la string finale à retourner
        char resultString[500];

        //Check button name value
        if  (strcmp(nativeString, "READ") == 0)
        {
            int calcul = my_number * my_number;
            char numberToString[100];
            sprintf(numberToString, "%d", calcul); //Cast le resultat du calcul dans un char[]

            strcpy(resultString,"READ : ");        //Copy string "READ :" into the result.
            strcat(resultString, numberToString);       //Puis concatène le résultat du calcul

            return env->NewStringUTF(resultString);     //Auto conversion to const char* par la JNI lors du retour
        }
        else if (strcmp(nativeString, "WRITE") == 0)
        {
            int calcul = my_number * my_number * my_number;
            char numberToString[100];
            sprintf(numberToString, "%d", calcul);

            strcpy(resultString,"WRITE : ");
            strcat(resultString, numberToString);

            return env->NewStringUTF(resultString);
        }
    }
    else //Le nombre sort du range
    {
        return env->NewStringUTF("Error : Type a number between 1 and 10");
    }

}