package lang.ds.number;

public class Reference
{
    public static void main(String[] args)
    {
//        this.explain();
    }

    public class Site
    {
        public Site()
        {

        }
    }

    public void explain()
    {
        // 引用类型类似于c/c++指针
        // 引用类型指向一个对象，指向对象的时引用变量,
        // 这些变量在声明时被指定为特定的类型，一旦声明后就不可改变了
        //
        // 对象、数组都是引用类型
        // 默认值都为null
        // 一个引用变量可以用来引用任何与之兼容的类型
        Site site = new Site();
        System.out.println(site);
    }
}
