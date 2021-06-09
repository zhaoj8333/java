package mem;

public class PageFault {
    private static int[][] arr = new int[128][128];

    /**
     * 数组按行存放，假设页大小128字，所以每行需要1页
     *
     */
    public static void main(String[] args) {
//        System.out.println(Arrays.toString(arr));
//        System.exit(0);
//        test1(); // by line
//        test2(); // by column



    }

    private static void test3() {
        char s = new Character('A');
        int i;
    }

    /**
     * 此处是逐列访问
     *
     * 内存地址跳跃很远，不满足空间局部性，导致缓存命中失败
     */
    private static void test2() {
//        Stopwatch sw = new Stopwatch();
        // 而此处会产生128*128个PageFault
        for (int t = 0; t < 100000; t++) {
            for (int j = 0; j < arr.length; j++) {
                for (int i = 0; i < arr[j].length; i++) {
                    arr[i][j] = 2;
                }
            }
        }
//        System.out.println("column: " + sw.elapsedTime());
    }

    /**
     * 此处是逐行访问
     */
    private static void test1() {
//        Stopwatch sw = new Stopwatch();
        // 此处产生128个PageFault
        for (int t = 0; t < 100000; t++) {
            for (int i = 0; i < arr.length; i++) {
                for (int j = 0; j < arr[i].length; j++) {
                    arr[i][j] = 1;
                }
            }
        }
//        System.out.println("line: " + sw.elapsedTime());
    }
}
