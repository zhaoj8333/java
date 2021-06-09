package design.proxy;

import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.io.IOException;

public class Tool {

    public static void genClass(Object proxy) {
        FileOutputStream out = null;
        try {
            byte[] proxyClass = ProxyGenerator.generateProxyClass(
                    proxy.getClass().getSimpleName(),
                    new Class[] {proxy.getClass()}
            );
            System.out.println(proxy.getClass().getSimpleName());
            out = new FileOutputStream(proxy.getClass().getSimpleName() + ".class");
            out.write(proxyClass);
        } catch (Exception e) {
            System.err.println("error generating");
            System.err.println(e.getMessage());
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
