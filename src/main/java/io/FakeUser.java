package io;


import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * 内存映射文件：
 *     将一段虚拟内存逐字节映射于一个文件，使得应用程序处理文件如同访问主内存
 *
 *     内存映射适用于大文件。
 *     小文件因为内存映射要对其边界，最小单位是4kb
 */
public class FakeUser {
    private int lineAmount;
    private String path;

    private Connection conn;
    private Statement stmt;
    private ResultSet rs;

    public FakeUser(int lineAmount, String path) {
        this.lineAmount = lineAmount;
        this.path = path;
    }

    private static class CsdnUser {
        private String userName;
        private String passWord;
        private String mailBox;
        private String updated;

        public CsdnUser(String userName, String passWord, String mailBox, String updated) {
            this.userName = userName;
            this.passWord = passWord;
            this.mailBox = mailBox;
            this.updated = updated;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassWord() {
            return passWord;
        }

        public void setPassWord(String passWord) {
            this.passWord = passWord;
        }

        public String getMailBox() {
            return mailBox;
        }

        public void setMailBox(String mailBox) {
            this.mailBox = mailBox;
        }

        public String getUpdated() {
            return updated;
        }

        public void setUpdated(String updated) {
            this.updated = updated;
        }
    }

    private int count = 0;

    private static String name = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_";
    private static String pass = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_-=+/;:',./<>?~`!@#$%^&*()";
    private static String coms = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static String orgs = "abcdefghijklmnopqrstuvwxyz";

    private static String randomStr(String chars) {
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        try {
            for (int i = 0; i < r.nextInt(20); i++) {
                int number = r.nextInt(chars.length() - 1);
                sb.append(chars.charAt(number));
            }
        } catch (Exception e) {
            return "";
        }
        return sb.toString();
    }

    private String getName() {
        String username = randomStr(name);
        if (username.length() < 3) {
            username = username + randomStr(username);
        }
        return username;
    }

    private String getPassword() {
        StringBuilder password = new StringBuilder(randomStr(pass));
        while (password.length() < 7) {
            String r = randomStr(pass);
            password.append(r);
        }
        return password.toString();
    }

    private String getMalbox() {
        return getName() + "@" + randomStr(coms) + "." + randomStr(orgs);
    }

    public void exec() {
        File file  = new File(path);
        ArrayList<HashMap<String, String>> list = new ArrayList<>(lineAmount);
        //            FileInputStream fis = new FileInputStream(file);
//            Scanner sc = new Scanner(fis);
        while (true) {
//            while (sc.hasNextLine()) {
//                String line = sc.nextLine();
            HashMap<String, String> row = new HashMap<>();
//                String[] split = line.split(" # ");
            row.put("username", getName());
            row.put("password", getPassword());
            row.put("mailbox",  getMalbox());
            long timestamp = System.currentTimeMillis();
            int r = (new Random()).nextInt(2000000000);
            row.put("updated", (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).format(new Date((timestamp - r))));
            list.add(row);

            if (list.size() == lineAmount) {
                storeToMysql(list);
//                    StdOut.println(row);
                list.clear();
                count += lineAmount;
            }
        }
//        if (list.size() > 0) {
//            storeToMysql(list);
//            StdOut.println("done ===================== ");
//        }
    }

    private void initServer() {
        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            conn = DriverManager.getConnection(
                    "jdbc:mysql://192.168.33.10:3306/sakila",
                    "root",
                    "zhaoj833"
            );
            stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("successfully connected to mysql-server");
    }

    private void initTable() {
        try {
            String user = " CREATE TABLE IF NOT EXISTS `user` (\n" +
                    "  `id` int unsigned NOT NULL PRIMARY KEY AUTO_INCREMENT,\n" +
                    "  `username` varchar(45) NOT NULL default \"\",\n" +
                    "  `password` varchar(45) NOT NULL default \"\",\n" +
                    "  `mailbox` varchar(45) NOT NULL default \"\",\n" +
                    "  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 \n";
            int i = stmt.executeUpdate(user);
            System.out.println("create: " + i);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void storeToMysql(ArrayList<HashMap<String, String>> list) {
        StringBuilder insert = new StringBuilder("INSERT INTO `user` (`id`, `username`, `password`, `mailbox`, `updated`) values ");
        for (int i = 0; i < list.size(); i++) {
            insert.append("\n");
            insert.append("(");
            insert.append("null").append(", ");
            insert.append("\"").append(list.get(i).get("username")).append("\", ");
            insert.append("\"").append(list.get(i).get("password")).append("\", ");
            insert.append("\"").append(list.get(i).get("mailbox")).append("\", ");
            insert.append("\"").append(list.get(i).get("updated")).append("\"");
            insert.append(")");
            if (i != list.size() - 1) {
                insert.append(", ");
            }
        }
//        StdOut.println(insert);
        int i = 0;
        try {
            i = stmt.executeUpdate(insert.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("inserted : " + count + " rows");
//        StdOut.println("\ninserted: " + i + " rows");
    }

    public void free() {
        try {
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        FakeUser mmf = new FakeUser(5000, "/home/allen/sdb1/vagrant_data/www.csdn.net.sql");

        mmf.initServer();
        mmf.initTable();
//        System.exit(0);
        mmf.exec();
        mmf.free();
    }
}
