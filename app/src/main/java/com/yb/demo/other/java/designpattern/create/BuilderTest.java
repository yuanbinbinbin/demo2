package com.yb.demo.other.java.designpattern.create;

/**
 * 建造者模式
 * 主要解决：简化复杂对象的创建
 * 关键点:在不同的场景中：复杂对象的 一些参数可变、一些参数比变
 * 使用场景：StringBuilder、AlertDialog、Notification、OkHttp new Request.Builder().url("http://www.baidu.com").build()
 * Created by yb on 2017/12/14.
 */
public class BuilderTest {

    public static class User {
        private String id;//必选
        private String name;//必选
        private String sex;//可选
        private short age;//可选
        private String phone;//可选
        private String address;//可选

        User(Builder builder) {
            this.id = builder.id;
            this.name = builder.name;
            this.sex = builder.sex;
            this.age = builder.age;
            this.phone = builder.phone;
            this.address = builder.address;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public short getAge() {
            return age;
        }

        public void setAge(short age) {
            this.age = age;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public static class Builder {
            private String id;//必选
            private String name;//必选
            private String sex;//可选
            private short age;//可选
            private String phone;//可选
            private String address;//可选

            public Builder(String id, String name) {
                this.id = id;
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public short getAge() {
                return age;
            }

            public void setAge(short age) {
                this.age = age;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public User build() {
                return new User(this);
            }
        }
    }

// ----------------   AlertDialog.Builder
//    private void showDialog(){
//        AlertDialog.Builder builder=new AlertDialog.Builder(context);
//        builder.setIcon(R.drawable.icon);
//        builder.setTitle("Title");
//        builder.setMessage("Message");
//        builder.setPositiveButton("Button1", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                //TODO
//            }
//        });
//        builder.setNegativeButton("Button2", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                //TODO
//            }
//        });
//
//        builder.create().show();
//    }

// -------------------   Notification从API11开始也采用了Builder模式
//
//    Notification.Builder builder = new Notification.Builder(this);
//    Intent intent = new Intent(this, MainActivity.class);
//    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//    builder.setContentIntent(pendingIntent)
//            .setSmallIcon(R.drawable.ic_launcher)
//    .setWhen(System.currentTimeMillis())
//            .setContentText("内容")
//    .setContentTitle("标题")
//    .setTicker("状态栏上显示...")
//    .setNumber(2)
//    .setOngoing(true);
//    //API11
//    Notification notification = builder.getNotification();
//
//    //API16
//    Notification notification = builder.build();

//  ----------------  OkHttp中OkHttpClient的创建
//
//
//    OkHttpClient  okHttpClient = new OkHttpClient.Builder()
//            .cache(getCache())
//            .addInterceptor(new HttpCacheInterceptor())
//            .addInterceptor(new LogInterceptor())
//            .addNetworkInterceptor(new HttpRequestInterceptor())
//            .build();


//   ------------------- Retrofit中Retrofit对象的创建
//
//
//    Retrofit retrofit = new Retrofit.Builder()
//            .client(createOkHttp())
//            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//            .baseUrl(BASE_URL)
//            .build();


}
