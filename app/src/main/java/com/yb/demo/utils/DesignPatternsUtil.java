package com.yb.demo.utils;

import java.util.ArrayList;
import java.util.List;

//设计模式
public class DesignPatternsUtil {

    public static void main(String[] args) {
        new ChainDesignPattern().run();
    }

    //region 责任链设计模式
    public static class ChainDesignPattern {

        private void run() {
            List<Interceptor> interceptors = new ArrayList<>();
            interceptors.add(new Interceptor1());
            interceptors.add(new Interceptor2());
            interceptors.add(new Interceptor3());
            interceptors.add(new Interceptor4());

            Chain chain = new RealChain(0, interceptors, "request");
            chain.proceed("123");
        }

        public static class Interceptor1 implements Interceptor {

            @Override
            public String interceptor(Chain chain) {
                System.out.println("interceptor1 start");
                String result = chain.proceed(chain.request());
                System.out.println("interceptor1 end : " + result);
                return result;
            }
        }

        public static class Interceptor2 implements Interceptor {

            @Override
            public String interceptor(Chain chain) {
                System.out.println("interceptor2 start");
                String result = chain.proceed(chain.request());
                System.out.println("interceptor2 end: " + result);
                return result;
            }
        }

        public static class Interceptor3 implements Interceptor {

            @Override
            public String interceptor(Chain chain) {
                System.out.println("interceptor3 start");
                String result = chain.proceed(chain.request());
                System.out.println("interceptor3 end " + result);
                return result;
            }
        }

        public static class Interceptor4 implements Interceptor {

            @Override
            public String interceptor(Chain chain) {
                System.out.println("interceptor4 process");
                return "success";
            }
        }

        interface Interceptor {

            String interceptor(Chain chain);
        }

        interface Chain {
            String request();

            String proceed(String request);
        }

        public static class RealChain implements Chain {
            int index;
            List<Interceptor> interceptors;
            String request;

            public RealChain(int index, List<Interceptor> interceptors, String request) {
                this.index = index;
                this.interceptors = interceptors;
                this.request = request;
            }

            @Override
            public String request() {
                return request;
            }

            @Override
            public String proceed(String request) {
                if (index >= interceptors.size()) return "";
                Interceptor interceptor = interceptors.get(index);
                Chain chain = new RealChain(index + 1, interceptors, request);
                return interceptor.interceptor(chain);
            }
        }

    }
    //endregion

    //region 单例模式
    public static class SingleDesignPattern1 {
        private static volatile SingleDesignPattern1 instance;

        private SingleDesignPattern1() {
        }

        public static SingleDesignPattern1 getInstance() {
            if (instance == null) {
                synchronized (SingleDesignPattern1.class) {
                    if (instance == null) {
                        instance = new SingleDesignPattern1();
                    }
                }
            }
            return instance;
        }
    }

    public static class SingleDesignPattern2 {

        private SingleDesignPattern2() {
        }

        public static SingleDesignPattern2 getInstance() {
            return InnerSingleDesignPattern2.instance;
        }

        private static class InnerSingleDesignPattern2 {
            private static final SingleDesignPattern2 instance = new SingleDesignPattern2();
        }
    }

    //endregion

    //region 工厂模式
    public static class FactoryDesignPattern {

        public static Product createProduct(int type) {
            switch (type) {
                case 1:
                    return new Product1();
                case 2:
                    return new Product2();
            }
            return null;
        }

        public static abstract class Product {
            abstract void method1();
        }

        public static class Product1 extends Product {

            @Override
            void method1() {

            }
        }

        public static class Product2 extends Product {

            @Override
            void method1() {

            }
        }

    }
    //endregion

}
