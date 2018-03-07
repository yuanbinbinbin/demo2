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

    //字符串拼接#include <string.h>
    strcat(result,resultBody);

    //释放JVM字符串
    (*env)->ReleaseStringUTFChars(env,str,resultBody);

    //生成 Java 中的字符串对象
    //指针的指针
    // env <=> JNINativeInterface** C语言
    return (*env)->NewStringUTF(env, result);
}


JNIEXPORT void JNICALL Java_com_base_baselibrary_jni_simple_Simple1_printLog(JNIEnv *env, jclass type,jstring str) {

    //获取JVM层字符串
    char* log = (*env)->GetStringUTFChars(env,str,JNI_FALSE);

    if(log == NULL){
        return ;
    }

    char * tag = "Native Log Print";


    // 调用Android的代码
    // 代码需要调用系统的日志库, 这个库已经在 CMakeList.txt添加了
    //#include <android/log.h>
    __android_log_write(ANDROID_LOG_VERBOSE,tag,log);


    //释放JVM字符串
    (*env)->ReleaseStringUTFChars(env,str,log);
}

