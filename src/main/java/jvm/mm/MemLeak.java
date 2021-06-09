package jvm.mm;

import java.sql.*;


/**
 * 内存泄露问题:
 *
 * 原因:
 *     对象定义在错误的范围
 *     异常处理不当
 *     集合数据管理不当
 */
public class MemLeak {
    public static void main(String[] args) {

    }

    /**
     * 集合数据管理不当
     *
     * 当使用Array-based的数据结构(ArrayList, HashMap)时, 尽量减少resize
     *
     * 尽量估算好size, 减少resize不必要的array copying, gc碎片
     *
     * 如果一个List只需要顺序访问, 不适用随机访问, 用LinkedList代替, 链表不要要顺序访问
     */
    private void testCollectionManage() {

    }

    /**
     * 异常处理不当
     *
     * 如果doSomeStuff抛出异常, rs.close, conn.close不会被调用
     * 会导致内存泄漏和db连接泄露
     */
    private void testExceptionMisOp() {
        String url = "";
        String name = "";
        String passwd = "";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, name, passwd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            String sql = "do a query";
            assert conn != null;
            final PreparedStatement stmt = conn.prepareStatement(sql);
            final ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                doSomeStuff();
            }
            // 关闭资源应放入finally块
            rs.close();
            conn.close();
        } catch (Exception e) {
            // 抛出异常
        }
    }

    private void doSomeStuff() {
    }

    /**
     * 对象定义在错误范围
     *
     * 如果MemLeak对象生命较长, 会导致临时性内存泄漏
     * 这里的names变量其实只有临时作用
     *
     * 改进:
     *     将names设置为局部变量
     */
    private String[] names;
    private void testWrongScope(int length) {
        if (names == null || names.length < length) {
            names = new String[length];
        }
        populate(names);
//        print(names);
    }

    private void populate(String[] names) {
    }
}
