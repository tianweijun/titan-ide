package titan.ide.window.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import titan.ide.exception.IdeRuntimeException;

/**
 * .
 *
 * @author tian wei jun
 */
public class FileUtil {

  public static String getString(File file) {
    String str = "";
    try (FileInputStream inputStream = new FileInputStream(file)) {
      int length = inputStream.available();
      byte[] bytes = new byte[length];
      inputStream.read(bytes);
      str = new String(bytes, StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new IdeRuntimeException(e);
    }
    return str;
  }
}
