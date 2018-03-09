//
// Created by  on 2018/3/9.
//

#include <jni.h>
#include <string.h>
#include <sys/stat.h>
#include <sys/system_properties.h>
#include <android/log.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/socket.h>
#include <netdb.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <errno.h>

#define LOG_TAG "NativeVM" //指定打印到logcat的Tag
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

int i;

//检查特定文件是否存在
int checkFile(char *res) {
    struct stat buf;
    int result = stat(res, &buf) == 0 ? 1 : 0;
    if (result) {
        i++;
        LOGI("%s  exsits, emulator!", res);
        //     kill(getpid(),SIGKILL);
    }
    return result;
}

//检查系统属性是否存在
void checkProperty(char *res) {
    char buff[PROP_VALUE_MAX];
    memset(buff, 0, PROP_VALUE_MAX);
    int result =
            __system_property_get(res, (char *) &buff) > 0 ? 1 : 0; //返回命令行内容的长度
    if (result != 0) {
        LOGI("%s %s  exsits, emulator!", res, buff);
        //  kill(getpid(),SIGKILL);
        i++;
    }
}

//检查系统属性的某个值是否包含特定字符
void checkPropertyValueContains(char *res, char *val) {
    char buff[PROP_VALUE_MAX + 1];
    memset(buff, 0, PROP_VALUE_MAX + 1);
    int lman = __system_property_get(res, buff);
    if (lman > 0) {
        if (strstr(buff, val) != NULL) { // match!
            LOGI("%s property value contains %s . Emulator!", res, val);
            i++;
        }
    }
}

//获取CPU信息
char *getCpuInfo() { //获取cpu型号
    //此处在测试时去判断cpu型号是否是intel core，至强或者奔腾，AMD系列，x86手机cpu型号为intel atom，arm一般为联发科，高通，麒麟等等
    //如是判断为前者，则认为当前环境为模拟器

    char *info[128];
    memset(info, 0, 128);
//    char *res = new char[256];
//    memset(res,0,256);
    char *split = ":";
    char *cmd = "/proc/cpuinfo";
    FILE *ptr;
    if ((ptr = fopen(cmd, "r")) != NULL) {
        while (fgets(info, 128, ptr)) {
            char *tmp = NULL;
            //去掉换行符
            if (tmp = strstr(info, "\n"))
                *tmp = '\0';
            //去掉回车符
            if (tmp = strstr(info, "\r"))
                *tmp = '\0';
            //strstr(str1,str2)  判断str2 是否是str1的子串
            //strtok(str1,str2) 分解字符
            if (strstr(info,
                       "Hardware")) {  //真机一般会获取到hardware，示例：Qualcomm MSM 8974 HAMMERHEAD (Flattened Device Tree)
                strtok(info, split);
                char *s = strtok(NULL, split);
                return s;
            }
        }
        //指针指向开头
        rewind(ptr);
        while (fgets(info, 128, ptr)) {
            char *tmp = NULL;
            //去掉换行符
            if (tmp = strstr(info, "\n"))
                *tmp = '\0';
            //去掉回车符
            if (tmp = strstr(info, "\r"))
                *tmp = '\0';

            //strstr(str1,str2)  判断str2 是否是str1的子串
            //strtok(str1,str2) 分解字符
            if (strstr(info,
                       "model name")) { //测试了一个模拟器，取到的是model_name，示例：Intel(R) Core(TM) i5-4590 CPU @ 3.30GHz
                strtok(info, split);
                char *s = strtok(NULL, split);
                return s;
            }
        }

    } else {
        LOGI("NULLLLLLLLL");
    }
    return NULL;
}

//获取内核信息
char *getKernelInfo() {
//获取设备版本，真机示例：Linux version 3.4.0-cyanogenmod (ls@ywk) (gcc version 4.7 (GCC) ) #1 SMP PREEMPT Tue Apr 12 11:38:13 CST 2016
// 海马玩：   Linux version 3.4.0-qemu+ (droid4x@CA) (gcc version 4.6.3 (Ubuntu/Linaro 4.6.3-1ubuntu5) ) #25 SMP PREEMPT Tue Sep 22 15:50:48
    //腾讯模拟器中包含了tencent字眼
    char *info[256];
    memset(info, 0, 256);
    char *cmd = "/proc/version";
    FILE *ptr;
    if ((ptr = fopen(cmd, "r")) != NULL) {
        while (fgets(info, 256, ptr)) {
            char *tmp = NULL;
            if (tmp = strstr(info, "\n"))
                *tmp = '\0';
            //去掉回车符
            if (tmp = strstr(info, "\r"))
                *tmp = '\0';
            return info;
        }
    } else {
        LOGI("NULLLLLLLLL");
        return NULL;
    }
}


JNIEXPORT jstring JNICALL Java_com_base_baselibrary_jni_vm_CheckVMUtil_getCpuInfo(JNIEnv *env) {
    char *s = getCpuInfo();
    return (*env)->NewStringUTF(env, s == NULL ? "" : s);
}

JNIEXPORT jstring JNICALL Java_com_base_baselibrary_jni_vm_CheckVMUtil_getKernelInfo(JNIEnv *env) {
    char *s = getKernelInfo();
    return (*env)->NewStringUTF(env, s == NULL ? "" : s);
}

JNIEXPORT jint JNICALL Java_com_base_baselibrary_jni_vm_CheckVMUtil_check(JNIEnv *env) {
    i = 0;
    checkFile("/system/bin/qemu_props"); //检测原生模拟器
    // checkFile("/system/bin/qemud");  //小米会检测出此项
    checkFile("/system/bin/androVM-prop");
    checkFile("/system/bin/microvirt-prop");//逍遥
    checkFile("/system/lib/libdroid4x.so"); //海马玩
    checkFile("/system/bin/windroyed");//文卓爷
    checkFile("/system/bin/microvirtd");//逍遥
    checkFile("/system/bin/nox-prop"); //夜神
    checkFile("/system/bin/ttVM-prop"); //天天
    checkFile("/system/bin/droid4x-prop"); //海马玩
    checkFile("/data/.bluestacks.prop");//bluestacks
    checkProperty("init.svc.vbox86-setup"); //基于vitrualbox
    checkProperty("init.svc.droid4x"); //海马玩
    checkProperty("init.svc.qemud");
    checkProperty("init.svc.su_kpbs_daemon");
    checkProperty("init.svc.noxd"); //夜神
    checkProperty("init.svc.ttVM_x86-setup"); //天天
    checkProperty("init.svc.xxkmsg");
    checkProperty("init.svc.microvirtd");//逍遥
//    checkProperty("ro.secure");   //检测selinux是否被关闭，一般手机均开启此选项
    checkProperty("ro.kernel.android.qemud");
    //  checkProperty("ro.kernel.qemu.gles"); //三星SM-G5500误报此项
    checkProperty("androVM.vbox_dpi");
    checkProperty("androVM.vbox_graph_mode");
    checkPropertyValueContains("ro.product.manufacturer",
                               "Genymotion"); // Genymotion check ,thx alinbaturn

    char *s = getCpuInfo();
    //x86架构的移动处理器为Intel(R) Atom(TM)
    if (s == NULL || strstr(s, "Intel(R) Core(TM)") || strstr(s, "Intel(R) Pentium(R)") ||
        strstr(s, "Intel(R) Xeon(R)") || strstr(s, "Genuine Intel(R)") ||
        strstr(s, "AMD")) { //分别为最常见的酷睿，奔腾，至强，AMD处理器
        i++;
    }
    LOGI("the cpu native info is %s", s);

    //包含qemu+或者tencent均为模拟器
    s = getKernelInfo();
    if (s == NULL || strstr(s, "qemu+") || strstr(s, "tencent") || strstr(s, "virtualbox")) {
        i++;
    }
    LOGI("the kernel info is %s", s);

    return i;
}
