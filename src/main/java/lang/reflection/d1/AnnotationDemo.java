package lang.reflection.d1;

/**
 * @author allen
 */
@interface Allen {
    String name();
    int age();
}

@Allen(name = "allen", age = 12)
public class AnnotationDemo {
    public static void main(String[] args) {
//        System.out.println(Allen.class);
        Package[] pack = Package.getPackages();
        for (int i = 0; i < pack.length; i++) {
//            System.out.println(pack[i]);

            System.out.println(pack[i].isAnnotationPresent(Allen.class));
        }

    }
}
