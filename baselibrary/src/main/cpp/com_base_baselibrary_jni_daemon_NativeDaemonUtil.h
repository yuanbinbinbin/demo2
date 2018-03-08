//
// Created by yb on 2018/3/7.
//

#ifndef DEMO_COM_BASE_BASELIBRARY_JNI_DAEMON_START_H
#define DEMO_COM_BASE_BASELIBRARY_JNI_DAEMON_START_H

#endif //DEMO_COM_BASE_BASELIBRARY_JNI_DAEMON_START_H

#include <jni.h>
#include <string.h>
#include <android/log.h>
#include <sys/inotify.h>
#include <unistd.h>
#include<sys/file.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <stdlib.h>
#include <sys/system_properties.h>

#define LOG_TAG "NativeDaemon" //指定打印到logcat的Tag
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)