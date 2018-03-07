//
// Created by yb on 2018/3/6.
//

#include "com_base_baselibrary_jni_simple_Simple1.h"
// 代表可以被 Java 调用
JNIEXPORT

// 返回值类型
jstring

//声明遵守JNI Java 调用C的规则
JNICALL

Java_com_base_baselibrary_jni_simple_Simple1_sayHello(JNIEnv *env, jclass type,jstring str) {

    //获取JVM层字符串
    char* resultBody = (*env)->GetStringUTFChars(env,str,0);

    if(resultBody == NULL){
        return "null";
    }


    //获取JVM层字符串长度
//    jsize  resultBodyLength = (*env)->GetStringLength(env,str);

    char result[128] = "c get str: ";

    //字符串拼接
    strcat(result,resultBody);

    //释放JVM字符串
    (*env)->ReleaseStringUTFChars(env,str,resultBody);

    //生成 Java 中的字符串对象
    //指针的指针
    // env <=> JNINativeInterface** C语言
    return (*env)->NewStringUTF(env, result);
}

