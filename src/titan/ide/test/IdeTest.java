package titan.ide.test;

import java.nio.charset.Charset;
import titan.ide.logger.Logger;

/**
 * .
 *
 * @author tian wei jun
 */
public class IdeTest {
  public static void main(String[] args) {
    // new IdeApplication().run();
    // Lock lock = new Lock(IdeTest.class);
    System.out.println(Charset.defaultCharset());
    System.out.println(System.getProperty("file.encoding"));

    String optEncode = (String) System.getProperties().get("sun.jnu.encoding");
    String fileEncode = (String) System.getProperties().get("file.encoding");
    String jvmEncode = Charset.defaultCharset().displayName();
    System.out.println("操作系统默认的字符编码:" + optEncode);
    System.out.println("文件系统默认的字符编码:" + fileEncode);
    System.out.println("Java虚拟机默认的字符编码：" + jvmEncode);

    String str1 = "孙卫琴姐姐的《Java面向对象编程》";
    byte[] encodes = str1.getBytes(); // 获取str1的字节编码数组
    String str2 = new String(encodes); // 根据该字节编码数组创建一个字符串对象
    System.out.println(str2); // 打印：孙卫琴姐姐的《Java面向对象编程》

    Logger.debug("IdeTest", "IdeTest end");
  }
}
