package lang.enums;

@SuppressWarnings("all")
enum Color {
    RED, GREEN, BLUE;

    /**
     * 枚举类与普通类一样会用自己的变量,方法和构造函数,构造函数只能有private,外部无法调用
     * 可以包含具体,抽象方法, 每个实例必须使用抽象方法
     */
    private Color() {
        System.out.println(this.name() + " \t: constructing");
    }

    public void colorInfo() {
        System.out.println("color info");
    }
}
