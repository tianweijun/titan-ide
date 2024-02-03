package titan.ide.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
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

  public static File makeFile(String filePath) {
    File file = new File(filePath);
    if (file.exists()) {
      return file;
    }
    File fileParent = file.getParentFile();
    if (!fileParent.exists()) {
      boolean mkdirs = fileParent.mkdirs();
      if (!mkdirs) {
        throw new IdeRuntimeException("mkdirs failed at makeFile method in FileUtil");
      }
    }

    try {
      boolean hasCreated = file.createNewFile();
      if (!hasCreated) {
        throw new IdeRuntimeException("mkdirs failed at makeFile method in FileUtil");
      }
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
      int read = inputStream.read(bytes);
      if (read > 0) {
        str = new String(bytes, StandardCharsets.UTF_8);
      }
    } catch (IOException e) {
      throw new IdeRuntimeException(e);
    }
    return str;
  }

  public static void write(File file, String text) {
    try (FileWriter fileWriter = new FileWriter(file)) {
      fileWriter.write(text);
    } catch (IOException e) {
      throw new IdeRuntimeException(e);
    }
  }
}
