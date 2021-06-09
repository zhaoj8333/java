package jvm.bc;

import java.io.IOException;
import java.util.Date;

/**
 * 虚方法表: 链接阶段建立
 *     针对于动态分派的过程, 虚拟机会在类的方法区建立一个虚方法表的数据结构(virtualmethod table)
 *     针对于invokeinterface指令来说, jvm会建立一个叫做接口方法表的数据结构(interfacemethod table)
 *     两者类似, 前者针对于父类
 *
 * 虚方法表存放的是每个方法的实际调用的入口地址
 * 如果子类没有重写父类方法, 这样子类与父类的该方法完全一样, 子类的方法入口地址就直接指向了父类的该方法地址, 而不是复制一份
 *
 * 虚表的建立是在类的加载, 链接阶段进行的
 *
 * invokevirtual执行过程:
 *  1. 通过栈帧中的对象找到对象
 *  2. 分析对象头, 找到实际的class
 *  3. class中有vtable, 在类加载的连接阶段 根据方法的重写规则生成好
 *  4. 查表得到方法的具体地址
 *  5. 执行方法字节码
 */
public class VirtualMethod {
    public static void main(String[] args) {
        Animal animal = new Animal();
        Animal dog = new Dog();

        // 根据实际的类型去查找方法
        animal.eat("hello");
        dog.eat(new Date());

        try {
            final int read = System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 多态调用父类不存在的方法:
        //     dog.get(); // can not resolve method 'get' in animal
        //     -> 对于静态类型Animal来说, invokevirtual针对的是Animal.get()
        //
    }
}

class Animal {
    public void eat(String str) {
        System.out.println("animal str");
    }

    public void eat(Date date) {
        System.out.println("animal date");
    }
}

class Dog extends Animal {
    @Override
    public void eat(String str) {
        System.out.println("dog str");
    }

    @Override
    public void eat(Date date) {
        System.out.println("dog date");
    }

    public void get() {}

}
