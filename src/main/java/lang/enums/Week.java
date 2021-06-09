package lang.enums;

enum Week implements Scheduable {
    // 枚举类的所有实例 必须在第一行显示, 不需要new,不需要调用构造方法,每个变量都是public final static修饰的
    // 因此,枚举也是语法糖
    // 编译后, 所有内部实例都会成为内部类
    MONDAY(0, "周一") {
        @Override
        public void reSchedule() {
            System.out.println("周一 rescheduling");
        }
    },
    TUESDAY(1, "周二") {
        @Override
        public void reSchedule() {
            System.out.println("周二 rescheduling");
        }
    },
    WEDNESDAY(2, "周三") {
        @Override
        public void reSchedule() {
            System.out.println("周三 rescheduling");
        }
    },
    THURSDAY(3, "周四") {
        @Override
        public void reSchedule() {
            System.out.println("周三 rescheduling");
        }
    },
    FRIDAY(4, "周五") {
        @Override
        public void reSchedule() {
            System.out.println("周三 rescheduling");
        }
    },
    SATURDAY(5, "周六") {
        @Override
        public void reSchedule() {
            System.out.println("周三 rescheduling");
        }
    },
    SUNDAY(6, "周日") {
        @Override
        public void reSchedule() {
            System.out.println("周三 rescheduling");
        }
    };
    private int num;
    private String name;
    private Week(int num, String name) {
        this.num = num;
        this.name = name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getNum() {
        return num;
    }
    public String getName() {
        return name;
    }
    @Override
    public void schedule() {
        System.out.println("schedule in enum");
    }
    @Override
    public String toString() {
        return "Week{" +
                "num=" + num +
                ", name='" + name + '\'' +
                '}';
    }
    public abstract void reSchedule();
}
