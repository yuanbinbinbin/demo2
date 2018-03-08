//
// Created by yb on 2018/3/7.
//

#include "com_base_baselibrary_jni_daemon_NativeDaemonUtil.h"

//拉起主进程
//部分手机无法拉起
void pullUp_server(char *name) {
    int sdkVersion = get_version();
    LOGI("pull up service %d", sdkVersion);
    if (sdkVersion >= 17) {
        LOGI("> 17");
        //am 命令
        int ret = execlp("am", "am", "startservice", "--user", "0", "--include-stopped-packages",
                         "-n", name,
                         (char *) NULL);
        LOGI("pull up service result : %d", ret);
    } else {
        execlp("am", "am", "startservice", "-n",
               name,
               (char *) NULL);
        LOGI("else");
    }
}

void go2Url(char *url) {
    sleep(2);
    int sdkVersion = get_version();
    LOGI("go to url %d", sdkVersion);
    if (sdkVersion >= 17) {
        LOGI("> 17");
        //am 命令
        int ret = execlp("am", "am", "start", "--user", "0",
                         "-a", "android.intent.action.VIEW",
                         "-d", url,
                         (char *) NULL);
        LOGI("go to url result: %d", ret);
    } else {
        execlp("am", "am", "start",
               "-a", "android.intent.action.VIEW",
               "-d", url,
               (char *) NULL);
        LOGI("else");
    }
}

//获取版本号
int get_version() {
    char value[8] = "";
    //#include <sys/system_properties.h>
    __system_property_get("ro.build.version.sdk", value);
    //#include <stdlib.h>
    return atoi(value);
}

//检查app是否被卸载
int isAppUninstalled(char *packagePath) {
    //如果路径能够访问会返回0，则表示没有被删除
    return !access(packagePath, 0) == 0;
}

int addFileLock(JNIEnv *env, jclass type, jstring file) {
    LOGI("file lock start %d", getpid());
    char *filePath = (*env)->GetStringUTFChars(env, file, JNI_FALSE);

    int fd = open(filePath, O_RDWR | O_CREAT);

    if (fd > 0) {
        LOGI("try to lock file");
        //#include<sys/file.h> return 0 表示成功,-1表示失败
        return flock(fd, LOCK_EX);
    }
    return -1;
}

JNIEXPORT  void JNICALL
Java_com_base_baselibrary_jni_daemon_NativeDaemonUtil_start(JNIEnv *env, jclass type,
                                                            jstring serviceName,
                                                            jstring listenFile,
                                                            jstring pkgPath,
                                                            jstring url) {

    char *name = (*env)->GetStringUTFChars(env, serviceName, JNI_FALSE);
    char *listenPath = (*env)->GetStringUTFChars(env, listenFile, JNI_FALSE);
    char *packagePath = (*env)->GetStringUTFChars(env, pkgPath, JNI_FALSE);
    char *goUrl = (*env)->GetStringUTFChars(env, url, JNI_FALSE);

    LOGI("serviceName: %s", name);
//  #include <unistd.h>
    pid_t pid = fork();
    if (pid < 0) {

    } else if (pid > 0) {
        LOGI("run main process");
        //克隆成功,代码运行在父进程中
        //请求文件排他锁,必须在fork完后申请,因为fork的子进程会把主进程的文件描述fd copy一份，也会拥有文件锁.详情:http://blog.csdn.net/cywosp/article/details/30083015
        addFileLock(env, type, listenFile);
    } else {
        //休眠2秒，保证让父进程先加锁
        sleep(2);
        //代码运行在子进程中
        //#include <unistd.h>
        pid_t ppid = getppid();
        pid = getpid();
        LOGI("pid=%d", pid);
        LOGI("ppid=%d", ppid);
        LOGI("listenPath: %s", listenPath);

        //监听被锁进程
        int fd = open(listenPath, O_RDWR);
        LOGI("get fd");
        if (fd == -1) {
            LOGI("fd -1");
            (*env)->ReleaseStringUTFChars(env, serviceName, name);
            (*env)->ReleaseStringUTFChars(env, listenFile, listenPath);
            (*env)->ReleaseStringUTFChars(env, pkgPath, packagePath);
            (*env)->ReleaseStringUTFChars(env, url, goUrl);
            exit(0);
        } else {
            LOGI("request lock");
            //申请文件的排它锁
            if (flock(fd, LOCK_EX) == 0) {
                LOGI("get lock");
                // 关闭文件，锁自动释放
                close(fd);
                //等3秒 卸载需要时间、系统清理进程也需要时间、时间自己决定
                sleep(3);
                //检查app是否被卸载了
                if (isAppUninstalled(packagePath)) {
                    //app 被卸载拉
                    if (goUrl != NULL) {
                        go2Url(goUrl);
                    }
                } else {
                    //app 没有被卸载,准备复活service
                    pullUp_server(name);
                }
            }
            //释放资源
            (*env)->ReleaseStringUTFChars(env, serviceName, name);
            (*env)->ReleaseStringUTFChars(env, listenFile, listenPath);
            (*env)->ReleaseStringUTFChars(env, pkgPath, packagePath);
            (*env)->ReleaseStringUTFChars(env, url, goUrl);
            exit(0);
        }
    }
    //释放资源
    (*env)->ReleaseStringUTFChars(env, serviceName, name);
    (*env)->ReleaseStringUTFChars(env, listenFile, listenPath);
    (*env)->ReleaseStringUTFChars(env, pkgPath, packagePath);
    (*env)->ReleaseStringUTFChars(env, url, goUrl);
    LOGI("end---------");
}



