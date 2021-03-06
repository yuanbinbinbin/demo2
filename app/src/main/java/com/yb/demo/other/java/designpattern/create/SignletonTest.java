package com.yb.demo.other.java.designpattern.create;

/**
 * 单例模式
 * 主要解决：一个全局使用的类频繁地创建与销毁。
 * 何时使用：当您想控制实例数目，节省系统资源的时候。
 * 如何解决：判断系统是否已经有这个单例，如果有则返回，如果没有则创建。
 * 关键代码：构造函数是私有的。
 * 使用场景： application、用户帐号信息、屏幕宽高、Glied.width()饿汉模式、EventBus.getDefault 懒汉安全模式 与之对应的是:3 双重锁模式
 * Created by yb on 2017/12/11.
 */
public class SignletonTest {
    public static void main(String[] args) {

    }

    //饿汉模式 线程安全的、但是无法懒加载  推荐
    static class HungrySignleton {
        private static HungrySignleton instance = new HungrySignleton();

        private HungrySignleton() {
        }

        public static HungrySignleton getInstance() {
            return instance;
        }
    }

    //懒汉模式 实现懒加载，但线程不安全
    static class LazySignleton {
        private static LazySignleton instance;

        private LazySignleton() {
        }

        public static LazySignleton getInstance() {
            if (instance == null) {
                instance = new LazySignleton();
            }
            return instance;
        }
    }

    //懒汉安全模式1 实现懒加载，线程安全，但性能不好
    static class LazySafeSignleton1 {
        private static LazySafeSignleton1 instance;

        private LazySafeSignleton1() {
        }

        public static synchronized LazySafeSignleton1 getInstance() {
            if (instance == null) {
                instance = new LazySafeSignleton1();
            }
            return instance;
        }
    }

    //懒汉安全模式2 实现懒加载，线程安全，但性能不好
    static class LazySafeSignleton2 {
        private static LazySafeSignleton2 instance;

        private LazySafeSignleton2() {
        }

        public static synchronized LazySafeSignleton2 getInstance() {
            synchronized (SignletonTest.class) {
                if (instance == null) {
                    instance = new LazySafeSignleton2();
                }
            }
            return instance;
        }
    }

    //懒汉安全模式3 双重锁模式 实现懒加载，线程安全，性能改善
    static class LazySafeSignleton3 {
        private static LazySafeSignleton3 instance;

        private LazySafeSignleton3() {
        }

        public static synchronized LazySafeSignleton3 getInstance() {
            if (instance == null) {
                synchronized (SignletonTest.class) {
                    if (instance == null) {
                        instance = new LazySafeSignleton3();
                    }
                    return instance;
                }
            }else{
                return instance;
            }
        }
    }

    //静态内部类实现单例  实现懒加载、线程安全、性能好   推荐
    static class StaticInnerSignleton {
        private StaticInnerSignleton() {
        }

        public StaticInnerSignleton getInstance() {
            return SignletonInstanceHolder.instance;
        }

        private static class SignletonInstanceHolder {
            private static final StaticInnerSignleton instance = new StaticInnerSignleton();
        }
    }


}
