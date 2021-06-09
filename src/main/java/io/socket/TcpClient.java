package io.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TcpClient {
    public static void main(String[] args) throws IOException {
        demo1();
    }

    private static void demo1() throws IOException {
        Socket s = new Socket("127.0.0.1", 8888);
        OutputStream outputStream = s.getOutputStream();
        outputStream.write("hello, server".getBytes());

        InputStream is = s.getInputStream();
        byte[] bs = new byte[1024];
        int len = is.read(bs);
        System.out.println(new String(bs, 0, len));

        is.close();
        s.close();
    }
}
