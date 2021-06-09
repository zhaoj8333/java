package lang.grammer;

public class StaticMethod {
    //
    //    Static Variable:
    //        1. A static variable is a variable that has been allocated "statically", meaning that its
    //            lifetime is the entire run of the program.
    //        2. Static Variable is in contrast to shorter-lived automatic variables, whose storage is
    //            stack allocated and deallocated on the call stack.
    //        3. In contrast to objects, object's storage is dynamically allocated and deallocated in
    //            heap memory.
    //        4. Variable lifetime is contrasted with scope(where a variable can be used): g
    //            global
    //            local
    //           scope does not refer to lifetime, but often implies lifetime.
    //           Many languages, global variables are always static, but some are dynamic. while, local
    //           variables are generally automatic
    //        5. static memory allocation is the allocation of memory at compile time, before the associated
    //            program is executed.
    //        6. dynamic memory allocation or automatic memory allocation is allocated at runtime.
    //
    //        7. The absolute address addressing mode can only be used with static variables.
    //        8. static variables are the only kinds of variables who location is known by the compiler at
    //            compile time. When the program(executable or library) is loaded into memory, static variables
    //            are stored in the data segment of program's address space(if initialized), or the BSS segment
    //            (if uninitialized), and are stored in corresponding sections of object io.files prior to loading.
    //        9. in oo languages, static member variable, which is a class variable of a statically defined class.
    //            i.e., a member variable of a given class which is shared across all instances(objects)
    //        10. Object constants known at compile-time, such as string literals, are usually allocated statically.
    //
    public static String name;
    public static int age;

    //  1. 自动变量（局部变量）：
    //      局部作用域变量，在控制流进入变量作用域时自动分配存储空间，在离开作用域时释放空间
    //      自动变量的存储空间在调用栈上分配与释放
    //
    //  2.
    //  3. 存储类（非oo当中的类）
    //      是c族语言中，变量与函数的可访问性（作用域与生存期）：
    //      auto：    指定于在{}所限定的范围内的作用域内的局部变量，为缺省存储类，一般省略
    //      register: 提示编译器把局部变量或函数的形参尽可能放入cpu寄存器中，以便快速访问，因此变量的字节长度不应超过寄存器长度。
    //                不能用&符号去获取此变量的内存地址
    //      static:   全局变量的默认存储类，本意是指变量的值在程序生存期一直可以保持，而且变量需要初始化
    //                本编译单元内部可见，可访问，其他编译单元不可访问
    //      extern:   全局变量可被各个对象单元访问，表示变量在别处定义，不能在此处初始化
    //      mutable:  只能用于类的数据成员，不能用于普通变量。允许修改类的数据成员
    //      thread_local: 运行时不同线程的具有该变量的不同存储位置，即各有各的副本。具有thread_local存储类的变量必然具有static性质
    //
    //    1. static是静态修饰符
    //    2. 程序中，任何变量或代码在编译时由系统自动分配内存来存储，而静态就是
    //        指在编译后所分配的内存会一直存在，与程序有着相同生命周期的变量。
    //        只要程序在运行，那么这个内存块一直存在。
    //        编译程序在link的时候，为static分配地址到数据段（data segment）
    //        copy on write机制： 当so库中的static变量被改写时，os会重新分配物理内存并映射，原来static变量使用的进程地址不会改变。
    //
    //    3. java中所有东西都是对象，而对象的抽象就是类。类必须经过实例化才能访问，但是static成员可以通过类名直接访问
    //    4. java中没有全局变量的概念：
    //      被static修饰的成员变量与方法独立于该类的任何对象。也就是说，不依赖于特定实例。被类的所有实例共享。只要这个类被加载，jvm就能
    //      根据类名在运行时数据区的方法区找到他们。static可以在它的对象创建之前访问，无须引用任何对象。
    //      public修饰的static成员变量和方法本质是全局变量和全局方法，当声明所属类的对象时，不会生成static变量副本，而是所有实例共享
    //      private修饰static时，表示该成员或方法能够在该类的实例中使用
    //    5. static 变量：
    //      被static修饰的是静态变量，jvm值为static变量分配一次内存，在加载类的过程中就完成了静态变量的内存分配
    //      实例变量：一个类的实例一经创建，jvm就会为其分配一次内存。多个实例就会存在多个拷贝，互不影响
    //    6. static 方法：
    //      静态方法中不能用this或super关键字，不能访问类的实例变量或实例方法
    //    7. static 代码块(静态代码块)：
    //      在类中独立于类成员的代码块，可有多个。不在任何方法体内，jvm加载时会按照出现次序执行这些代码块，每个代码块只会执行一次。
    //
    //  java自动变量：java没有auto，register关于存储类的关键字。
    //      java编译器不允许使用没有明确初始化的局部变量
    //
    //
    public static void main(String[] args) {
        name = "name1";
        StaticMethod sm  = new StaticMethod();
        System.out.println("sm:  " + sm.name);
        name = "name2";
        StaticMethod sm1 = new StaticMethod();
        System.out.println("sm1: " + sm1.name);
    }

    static {
        System.out.println("static代码块执行");
    }
}
