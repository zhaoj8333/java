package lang.generics;

public class SimpleGen {
    /**
     *  Java泛型（lang.generics）:
     *      jdk5中引入的新特性，泛型提供了 编译时类型安全检测机制，该机制允许程序员在编译时检测到非法类型
     *
     *  泛型本质：
     *      参数化类型，也就是：所操作的数据类型被指定为一个参数
     *      使用java泛型，可以使用一个泛型方法对一个对象数组排序。然后，调用该方法对整形数组、浮点数组、字符串数组进行排序
     *
     *  泛型方法：
     *      接收不同的参数，根据传递给泛型方法的参数类型，编译器适当的处理没一个方法调用
     *  泛型方法规则：
     *      所有泛型方法声明都有一个类型参数声明部分（由尖括号分隔），该类型参数声明部分在方法返回类型之前（在下面例子中的<E>）。
     *      每一个类型参数声明部分包含一个或多个类型参数，参数间用逗号隔开。一个泛型参数，也被称为一个类型变量，是用于指定一个泛型类型名称的标识符。
     *      类型参数能被用来声明返回值类型，并且能作为泛型方法得到的实际参数类型的占位符。
     *      泛型方法体的声明和其他方法一样。注意类型参数只能代表引用型类型，不能是原始类型（像int,double,char的等）。
     *
     *  Java语言引入泛型的好处是安全简单。可以将运行时错误提前到编译时错误。
     *  所有的强制转换都是自动和隐式的，提高代码的重用率。
     */

    private Object obj;

    public SimpleGen(Object obj) {
        this.obj = obj;
    }

    public Object getObj() {
        return this.obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public void printType() {
        System.out.println(this.getClass().getName());
    }
}
