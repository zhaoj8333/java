package lang.maker_interface.randomaccess;

import java.nio.ByteBuffer;
import java.util.*;

/**
 * {@link RandomAccess} 表名支持快速访问（通常为恒定时间）随机访问。
 * 此接口的目的是允许通用算法更改其行为，以便应用于随机访问列表或顺序访问列表时提供良好的性能
 *
 * 用于操纵随机访问列表的最佳算法如：{@link java.util.ArrayList}可以应用于顺序访问列表时产生二次行为（如：{@link java.util.LinkedList}）
 * 鼓励通用列表算法在应用如果将其应用于顺序访问列表之前提供较差性能的算法时，检查给定列表是否为instanceof，并在必要时更改其行为以保证可接受的性能
 *
 * 如果一些List实现提供恒定的访问时间，这个List实现应该通常实现这个接口
 *
 * for (int i = 0, n = list.size(); i < n; i++) {
 *     list.get(i);
 * }
 *
 * @对于随机访问List，以上循环通常比以下循环更快，因为以上循环是使用索引进行随机访问
 * @而对于顺序访问List，以下循环比以上循环更快
 *
 * 以下是通过迭代器访问，是顺序访问
 * for (Interator i = list.iterator(); i.hasNext()) {
 *     i.next();
 * }
 *
 * 如果将随机访问列表的算法在顺序访问列表中可能会产生平方级别的复杂度震荡，比如：链表；
 * 所以在操作通用的list算法时需要判断给定的list是否是实现了 {@link RandomAccess}接口，以防止将随机访问算法应用到顺序访问列表中，造成复杂度震荡
 *
 */
public class JavaRandomAccess {
    public static void main(String[] args) {
        Collection<Integer> c = new ArrayList<>();
//        c.add(1);
//        c.add(2);
//        c.add(null);
//        Collection<Integer> c = new LinkedList<>();
//        c.add(1);
//        c.add(2);
//        c.add(null);
//        checkNull(c);

        binarySearch();

    }

    private static void binarySearch() {
        /**
         * {@link Collections.binarySearch()}中，会判断list是否是 {@link RandomAccess}的实例，如果是则执行indexedBinarySearch;
         * 否则执行iteratorBinarySearch,
         */
    }

    private static void checkNull(final Collection<?> c) {
//        System.out.println(c instanceof RandomAccess);
        if (c instanceof RandomAccess && c instanceof List) {
            final List<?> list = (List<?>) c;
            final int size = list.size();
            for (int i = 0; i < size; i++) {
                if (list.get(i) == null) {
                    throw new IllegalArgumentException("c contains null values");
                }
            }
        } else {
            for (final Object element : c) {
                if (element == null) {
                    throw new IllegalArgumentException("c contains null values");
                }
            }
        }

    }
}
