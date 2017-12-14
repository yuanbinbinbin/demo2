package com.yb.demo.other.java.designpattern;

/**
 * Created by yb on 2017/12/14.
 */
public class Start {
    //设计模式是一套被反复使用的、经过分类编目的、代码设计经验的总结。
    //使用设计模式是为了重用代码、让代码更容易被他人理解、保证代码可靠性。
    //设计模式总共有23种，可分为3大类，创建型模式、结构型模式、行为型模式

//    1	创建型模式
//    这些设计模式提供了一种在创建对象的同时隐藏创建逻辑的方式，而不是使用 new 运算符直接实例化对象。这使得程序在判断针对某个给定实例需要创建哪些对象时更加灵活。
//
//    工厂模式（Factory Pattern） create/FactoryTest.java   android 中的体现：Dialog 集中管理、  Fragment 集中管理
//    抽象工厂模式（Abstract Factory Pattern）create/AbstractFactory.java  工厂模式升级版 更加的灵活 android 中的体现:不同样式的LoadingDialog 由不同的Factory生成、QQ 换皮肤
//    单例模式（Singleton Pattern） create/SignletonTest.java  android 中的体现：application、用户帐号信息、屏幕宽高、Glied.width()饿汉模式、EventBus.getDefault 懒汉安全模式3 双重锁模式
//    建造者模式（Builder Pattern） create/BuilderTest.java android中的体现：StringBuilder、AlertDialog、Notification、OkHttp new Request.Builder().url("http://www.baidu.com").build()
//    原型模式（Prototype Pattern）create/Prototype.java  android中的体现：效果 ctrl+c ctrl+v 、 Intent、OkHttp


//    2	结构型模式
//    这些设计模式关注类和对象的组合。继承的概念被用来组合接口和定义组合对象获得新功能的方式。
//
//    适配器模式（Adapter Pattern）
//    桥接模式（Bridge Pattern）
//    过滤器模式（Filter、Criteria Pattern）
//    组合模式（Composite Pattern）
//    装饰器模式（Decorator Pattern）
//    外观模式（Facade Pattern）
//    享元模式（Flyweight Pattern）
//    代理模式（Proxy Pattern）

//    3	行为型模式
//    这些设计模式特别关注对象之间的通信。
//
//    责任链模式（Chain of Responsibility Pattern）
//    命令模式（Command Pattern）
//    解释器模式（Interpreter Pattern）
//    迭代器模式（Iterator Pattern）
//    中介者模式（Mediator Pattern）
//    备忘录模式（Memento Pattern）
//    观察者模式（Observer Pattern）
//    状态模式（State Pattern）
//    空对象模式（Null Object Pattern）
//    策略模式（Strategy Pattern）
//    模板模式（Template Pattern）
//    访问者模式（Visitor Pattern）

//    设计模式的六大原则

//    1、开闭原则（Open Close Principle）
//
//    开闭原则的意思是：对扩展开放，对修改关闭。在程序需要进行拓展的时候，不能去修改原有的代码，实现一个热插拔的效果。提高扩展性好，易于维护和升级。
//
//    2、里氏代换原则（Liskov Substitution Principle）
//
//    里氏代换原则中说，任何基类可以出现的地方，子类一定可以出现。LSP 是继承复用的基石，只有当派生类可以替换掉基类，且软件单位的功能不受到影响时，
//    基类才能真正被复用，而派生类也能够在基类的基础上增加新的行为。里氏代换原则是对开闭原则的补充。
//    实现开闭原则的关键步骤就是抽象化，而基类与子类的继承关系就是抽象化的具体实现，所以里氏代换原则是对实现抽象化的具体步骤的规范。
//    实现抽象的规范，实现子父类互相替换；

//    3、依赖倒转原则（Dependence Inversion Principle）
//
//    这个原则是开闭原则的基础，针对接口编程，依赖于抽象而不依赖于具体。
//
//    4、接口隔离原则（Interface Segregation Principle）
//
//    降低耦合度，接口单独设计，互相隔离；
//
//    5、迪米特法则，又称最少知道原则（Demeter Principle）
//
//    功能模块尽量独立，一个实体应当尽量少地与其他实体之间发生相互作用，使得系统功能模块相对独立。
//
//    6、合成复用原则（Composite Reuse Principle）
//
//    合成复用原则是指：尽量使用合成/聚合的方式，而不是使用继承。
}
