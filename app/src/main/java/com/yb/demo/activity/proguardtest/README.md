# Android 混淆

**article1 : [ProGuard代码混淆技术详解](https://www.cnblogs.com/cr330326/p/5534915.html)**

**article2 : [ProGuard 详解](http://blog.csdn.net/TheMeLove/article/details/61619587)**

**article3 : [android studio代码混淆注意问题 ](http://blog.csdn.net/jdsjlzx/article/details/51861460)**

## 混淆过程  <br>
1.开启混淆,在app目录下的build.gradle文件中配置

    android {
	    buildTypes {
	        release {
	            minifyEnabled true
	            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
	        }
	    }
    }

2.自定义混淆规则

路径当前module下，proguard-rules.pro文件，可选

    #okhttp  start
	-dontwarn okhttp3.**
	-dontwarn okio.**
	-dontwarn javax.annotation.**
	-dontwarn org.conscrypt.**
	# A resource is loaded with a relative path so the package of this class must be preserved.
	-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
	#okhttp end
	
	#rxjava start
	-dontwarn rx.**
	-keep class rx.** { *; }
	-dontwarn sun.misc.**
	-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
	 long producerIndex;
	 long consumerIndex;
	}
	-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
	 rx.internal.util.atomic.LinkedQueueNode producerNode;
	}
	-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
	 rx.internal.util.atomic.LinkedQueueNode consumerNode;
	}
	#rxjava end


##  混淆结果对比  <br>

- 混淆前
<img src="https://github.com/yuanbinbinbin/demo2/blob/master/onlinepic/proguardBefore1.png" alt="proguardBefore1.png" />
<br>
<img src="https://github.com/yuanbinbinbin/demo2/blob/master/onlinepic/proguardBefore2.png" alt="proguardBefore2.png" />


- 混淆后
<img src="https://github.com/yuanbinbinbin/demo2/blob/master/onlinepic/proguardAfter1.png" alt="proguardAfter1.png" />
<br>
<img src="https://github.com/yuanbinbinbin/demo2/blob/master/onlinepic/proguardAfter2.png" alt="proguardAfter2.png" />

##  apk文件  <br>

**[混淆前]()**

**[混淆后]()**