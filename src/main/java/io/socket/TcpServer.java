package io.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author allen
 */
public class TcpServer {
    public static void main(String[] args) throws IOException {
        demo1();
    }

    private static void demo1() throws IOException {
        ServerSocket ss = new ServerSocket(8888);
        while (true) {
            Socket s = ss.accept();

            InputStream is = s.getInputStream();
            byte[] bs = new byte[1024];
            int len = is.read(bs);
            System.out.println(new String(bs, 0, len));

            OutputStream out = s.getOutputStream();
            out.write("server response".getBytes());

//        is.close();
//        ss.close();
//        s.close();
        }
    }
}
