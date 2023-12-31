package titan.ide.utli;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import titan.ide.exception.IdeRuntimeException;

/**
 * 文件操作工具类.
 *
 * @author tian wei jun
 */
public class FileUtil {
  private FileUtil() {}

  public static File makeFileDirectory(String fileDirectory) {
    File file = new File(fileDirectory);
    if (!file.exists()) {
      boolean hasCreadted = file.mkdirs();
      if (!hasCreadted) {
        throw new IdeRuntimeException("file.mkdirs failed.");
      }
    }
    return file;
  }

  public static File makeFile(String filePath) {
    File file = new File(filePath);
    File fileParent = file.getParentFile();
    if (!fileParent.exists()) {
      fileParent.mkdirs();
    }
    try {
      boolean hasCreated = file.createNewFile();
    } catch (IOException e) {
      throw new IdeRuntimeException(e);
    }
    return file;
  }

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
