import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.lang.reflect.Method;

/**
 * 2.（必做）自定义一个 Classloader，加载一个 Hello.xlass 文件，执行 hello 方法，此文件内容是一个 Hello.class 文件所有字节（x=255-x）处理后的文件。文件群里提供。
 *
 * @date 2020/10/20
 */
public class LoadxClass extends ClassLoader {

    private String dir = "/Users/xuxh/IdeaProjects/untitled1/untitled/src/Hello.xlass";

    public static void main(String[] args) {
        try {
            Class<?> helloClass = new LoadxClass().findClass("Hello");
            Object obj = helloClass.newInstance();
            Method helloMethod = helloClass.getMethod("hello");
            helloMethod.invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(dir));
            int len = bis.available();
            byte[] bytes = new byte[len];

            bis.read(bytes, 0, len);

            byte[] b = new byte[len];
            for (int i = 0; i < bytes.length; i++) {
                b[i] = (byte) (255 - bytes[i]);
            }

            return defineClass(name, b, 0, b.length);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ClassNotFoundException(name);
        }
    }
}
