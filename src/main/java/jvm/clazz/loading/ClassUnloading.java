package jvm.clazz.loading;

public class ClassUnloading {
    public static void main(String[] args) {

    }

    /**
     * 类的卸载:
     *     由jvm自带的类自带的类加载器所加载的类,在jvm的整个生命周期中,始终不会被卸载
     *     自带类加载器:
     *         根类加载器
     *         扩展类加载器
     *         系统类加载器
     *
     *     jvm本身会始终引用这些加载器,而这些类加载器则会始终引用他们所加载的Class对象,因此这些Class对象始终是可触及的
     *
     *     由用户自定义的类加载器所加载的类是可以被卸载的
     *
     * 类加载器的内部实现中,用一个java集合来存放所加载类的引用
     * 一个Class对象总是会引用它的类加载器, 调用Class对象的getClassLoader就可以获取其类加载器
     * 被加载的类与之对应的类加载器为双向关联关系
     *
     *
     */
}
