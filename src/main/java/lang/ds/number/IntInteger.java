package lang.ds.number;

public class IntInteger {
    public static void main(String[] args) {
        // int 与 integer比较
//        compare();
//        Integer a = 59;
//        int b = 59;
//        Integer c = Integer.valueOf(59);
//        Integer d = new Integer(59);
//        System.out.println(b == d);
//        System.out.println(a == b);
//        System.out.println(a == c);
//        System.out.println(c == d);
//        System.out.println(c);
//        System.out.println(d);

        integerClass();

    }

    private static void integerClass() {
//        System.out.println(Integer.MIN_VALUE);
//        System.out.println(Integer.MAX_VALUE);
//
//        System.out.println(Integer.TYPE);

//        System.out.println(Integer.toString(-10, -2));
//        System.out.println(Integer.toHexString('-'));
//        System.out.println('2d');

    }

    public static void compare() {
        
        // 内存占用：
        // Integer占用更多内存，需要存储对象元数据
        // int 是原始类型
        // 
        //  Java中没有任何无符号的类型（unsigned）。
        //
        // 对于引用类型变量，＝＝操作符比较的是是否指向同一个对象
        // 而基本变量，＝＝对比的是两个变量是否相等
        
        // Integer i = new Integer(1);
        // Integer j = new Integer(1);
        // System.out.println(i == j);  // 对象内存地址不同false php中是相等的
        
        // Integer i = 200;
        // Integer j = new Integer(200);
        // System.out.println(i == j);  // false
        
        // 非new生成的对象比较，如果值在[-128,127之间],true, 否则ｆａｌｓｅ
        // [-128,127]之间的数据会进行缓存,j1 = 127时，直接从缓存中取，不会再new了
        
        // Integer i1 = 127;
        // Integer j1 = 127;
        // System.out.println(i1 == j1); // true
        
        // Integer i2 = 128;
        // Integer j2 = 128;
        // System.out.println(i2 == j2); // false
            
        // Integer变量，无论是否是new与int比较，只要两个变量值相等，结果都为true
        Integer i1 = 200;
        Integer i2 = new Integer(200);
        System.out.println(i1 == i2); // 内存地址不一致：false
        System.out.println(i1); // 200
        System.out.println(i2); // 200

        int j = 200;
        System.out.println(i1 == j);
        System.out.println(i2 == j);
    
    }

}
