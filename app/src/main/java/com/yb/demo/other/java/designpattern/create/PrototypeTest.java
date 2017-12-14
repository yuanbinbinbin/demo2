package com.yb.demo.other.java.designpattern.create;

import java.util.ArrayList;

/**
 * 原型模式 通过克隆的方式创建对象 ctrl+c ctrl+v的效果
 * 应用：当想使用一个对象，改变一个属性，又想保存原来的属性时，保护性拷贝、Intent、OkHttp
 * 深拷贝 浅拷贝
 * 因为使用了super.clone() 底层是native方法，实际上是直接在内存中拷贝， 所以构造函数是不会执行的
 * Created by yb on 2017/12/14.
 */
public class PrototypeTest {
    //clone()方法并不属于Cloneable接口，Cloneable接口是空的，啥都没有，它仅仅起到一个标识的作用，表明这个对象是可以拷贝的
    //如果没有实现Cloneable接口却调用了super.clone()函数将抛出异常。CloneNotSupportedException
    public class Test implements Cloneable {//1.实现Cloneable 接口

        @Override
        protected Test clone() {//2.重写clone方法
            try {
                return (Test) super.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    //------------------深拷贝--------------
    static class Address implements Cloneable {
        private String zipCode;//邮编
        private String province;
        private String city;
        private String area;
        private String info;

        public Address(String zipCode, String province, String city, String area, String info) {
            this.zipCode = zipCode;
            this.province = province;
            this.city = city;
            this.area = area;
            this.info = info;
        }

        @Override
        protected Address clone() {
            Address address = null;
            try {
                address = (Address) super.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return address;
        }

        @Override
        public String toString() {
            return "Address{" +
                    "zipCode='" + zipCode + '\'' +
                    ", province='" + province + '\'' +
                    ", city='" + city + '\'' +
                    ", area='" + area + '\'' +
                    ", info='" + info + '\'' +
                    '}';
        }

        public String getZipCode() {
            return zipCode;
        }

        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }

    static class Student implements Cloneable {
        private String name;
        private String sex;
        private Address address;

        public Student(String name, String sex, Address address) {
            this.name = name;
            this.sex = sex;
            this.address = address;
        }

        @Override
        protected Student clone() {
            Student student = null;
            try {
                student = (Student) super.clone();
                student.address = address.clone();//不加这句就是浅复制 ，加上这句就是深复制
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return student;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public String getSex() {
            return sex;
        }

        public Address getAddress() {
            return address;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", sex='" + sex + '\'' +
                    ", address=" + address +
                    '}' + Integer.toHexString(hashCode());//获取对象的地址
        }
    }

    static class Clazz implements Cloneable {
        String name;
        ArrayList<Student> students;

        public Clazz(String name, ArrayList<Student> students) {
            this.name = name;
            this.students = students;
        }

        @Override
        protected Clazz clone() {
            Clazz clazz = null;
            try {
                clazz = (Clazz) super.clone();
                //不加下面的语句就是浅复制
                clazz.students = (ArrayList<Student>) students.clone();
                clazz.students.clear();
                //ArrayList 仅仅实现了浅复制，因此我们需要挨个clone
                for (Student student : students) {
                    clazz.students.add(student.clone());
                }
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return clazz;
        }

        @Override
        public String toString() {
            return "Clazz{" +
                    "name='" + name + '\'' +
                    ", students=" + students +
                    '}';
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ArrayList<Student> getStudents() {
            return students;
        }

        public void setStudents(ArrayList<Student> students) {
            this.students = students;
        }
    }

    public static void main(String[] args) {
        //student 与 address
        Address address = new Address("123", "北京", "北京", "海淀区", "中关村");
        Student student = new Student("小明", "男", address);
        Student student2 = student.clone();
        System.out.println(student);
        System.out.println(student2);
        student2.getAddress().setArea("朝阳区");
        System.out.println(student);
        System.out.println(student2);

        //student 与 Clazz
        ArrayList<Student> students = new ArrayList<Student>();
        students.add(student);
        students.add(student2);
        Clazz clazz = new Clazz("2-1", students);
        Clazz clazz2 = clazz.clone();
        System.out.println(clazz);
        System.out.println(clazz2);
        clazz2.getStudents().add(new Student("小红", "女", new Address("111", "河北", "石家庄", "裕华区", "XX小区")));
        System.out.println(clazz);
        System.out.println(clazz2);

    }

    //Intent中的Clone  深拷贝

//    @Override
//    public Object clone() {
//        return new Intent(this);
//    }
//    public Intent(Intent o) {
//        this.mAction = o.mAction;
//        this.mData = o.mData;
//        this.mType = o.mType;
//        this.mPackage = o.mPackage;
//        this.mComponent = o.mComponent;
//        this.mFlags = o.mFlags;
//        this.mContentUserHint = o.mContentUserHint;
//        if (o.mCategories != null) {
//            this.mCategories = new ArraySet<String>(o.mCategories);
//        }
//        if (o.mExtras != null) {
//            this.mExtras = new Bundle(o.mExtras);
//        }
//        if (o.mSourceBounds != null) {
//            this.mSourceBounds = new Rect(o.mSourceBounds);
//        }
//        if (o.mSelector != null) {
//            this.mSelector = new Intent(o.mSelector);
//        }
//        if (o.mClipData != null) {
//            this.mClipData = new ClipData(o.mClipData);
//        }
//    }


    //Okhttp 中的拷贝 浅拷贝
    /** Returns a shallow copy of this OkHttpClient. */
//    @Override
//    public OkHttpClient clone() {
//        return new OkHttpClient(this);
//    }
//    private OkHttpClient(OkHttpClient okHttpClient) {
//        this.routeDatabase = okHttpClient.routeDatabase;
//        this.dispatcher = okHttpClient.dispatcher;
//        this.proxy = okHttpClient.proxy;
//        this.protocols = okHttpClient.protocols;
//        this.connectionSpecs = okHttpClient.connectionSpecs;
//        this.interceptors.addAll(okHttpClient.interceptors);
//        this.networkInterceptors.addAll(okHttpClient.networkInterceptors);
//        this.proxySelector = okHttpClient.proxySelector;
//        this.cookieHandler = okHttpClient.cookieHandler;
//        this.cache = okHttpClient.cache;
//        this.internalCache = cache != null ? cache.internalCache : okHttpClient.internalCache;
//        this.socketFactory = okHttpClient.socketFactory;
//        this.sslSocketFactory = okHttpClient.sslSocketFactory;
//        this.hostnameVerifier = okHttpClient.hostnameVerifier;
//        this.certificatePinner = okHttpClient.certificatePinner;
//        this.authenticator = okHttpClient.authenticator;
//        this.connectionPool = okHttpClient.connectionPool;
//        this.network = okHttpClient.network;
//        this.followSslRedirects = okHttpClient.followSslRedirects;
//        this.followRedirects = okHttpClient.followRedirects;
//        this.retryOnConnectionFailure = okHttpClient.retryOnConnectionFailure;
//        this.connectTimeout = okHttpClient.connectTimeout;
//        this.readTimeout = okHttpClient.readTimeout;
//        this.writeTimeout = okHttpClient.writeTimeout;
//    }
}
