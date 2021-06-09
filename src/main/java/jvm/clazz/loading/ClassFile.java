package jvm.clazz.loading;

/**
 * 1.  MagicNumber: u4固定值
 *     0xCAFEBABE
 *
 * 2. 次版本号 : u2(2字节)
 *     00 00 (0)
 * 3. 主版本号: (u2(2字节)
 *     00 34 (52) -> 对应jdk 1.8
 * 4. 常量个数: constant_pool_count u2(2字节)
 *     00 19 (25 -1) = 24 0号常量被jvm占用, 表示什么都不引用
 *
 * 5. cp_info(N个字节): constant_pool常量池表
 *     数量: constant_pool_count - 1
 *
 */
public class ClassFile extends ClassLoading {

    public static int a = 1;
    public int b = 2;

    static {
        System.out.println("ClassFile static block");
        System.out.println("myClassLoader is : " + ClassFile.class.getClassLoader());
    }

    private ClassFile classFile;

    public ClassFile getClassFile() {
        return classFile;
    }

    public void setClassFile(Object classFile) {
        this.classFile = (ClassFile) classFile;
    }

    public static void main(String[] args) {

    }
    /**
     * 常量池分类: 每一种类型都有自己的结构
     *
     *
     *     字面量
     *
     */
/**/
}
