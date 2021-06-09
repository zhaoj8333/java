/*
    注解:
        与javadoc不同,注解可以通过反射获取内容.
        编译器生成class文件时,标注可以被嵌入到字节码中.

        jvm可以保留注解内容,运行时获取到注解内容

    内置注解:
        java.lang:
            @Override: 重写方法
            @Deprecated: 过时方法
            @SuppressWarnings: 忽略警告
            @SafeVarargs: 忽略任何使用参数为泛型变量方法或构造函数产生的警告
            @FunctionalInterface: 标志一个匿名函数或函数式接口
            @Repeatable: 标志某注解可以在同一声明上使用多次
        java.lang.annotation:
            @Documented: 标注这些注解是否包含在用户文档中
            @Retention: 用来标注注解, 标志该注解怎么保存, 是只在代码中,还是编入class文件, 或 运行时通过反射访问
            @Target: 用来标注注解, 标注这个注解应该是那种java成员
            @Inherited: 用来标注注解, 标注这个注解继承与哪个注解类

    * 一个注解都与一个RetentionPolicy关联: 一个Annotaion都有一个RetentionPolicy属性
        指定Annotation策略:
            SOURCE: 注解仅存在于编译期间, 如Override
            CLASS:  注解存在于class文件中,为默认行为
            RUNTIME: jvm会读取, 运行时有效
    * 一个注解都与一个或多个ElementType关联: 一个Annotation多有多个ElementType类型,
        如某个Annotion为METHOD类型,则只能修饰方法
    * Annotation有许多实现类,Deprecated, Documented, Inherited, Override等....

    作用:
        编译检查:
        反射中使用Annotation:
 */