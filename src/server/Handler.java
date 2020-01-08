package server;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author jing.liu14@ucarinc.com
 * @date 2019/12/11
 * @description:
 */
public class Handler{


    static{
        bean=111;
    }
    private static int bean=99;
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        MyLoader loader = new MyLoader();
        Class<?> aClass = loader.loadClass("Handler");
        Object o = aClass.newInstance();
        System.out.println(o.getClass());
        System.out.println(o instanceof Handler);
    }
}

class MyLoader extends ClassLoader{
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        try {
            InputStream resourceAsStream = getClass().getResourceAsStream(name);
            byte[] b = new byte[resourceAsStream.available()];
            return defineClass(name,b,0,b.length);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}