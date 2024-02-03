package titan.ide.window.text;

import java.io.File;

/**
 * .
 *
 * @author tian wei jun
 */
public class TreeNodeFileWrapper {

  public File file;

  public TreeNodeFileWrapper(File file) {
    this.file = file;
  }

  @Override
  public String toString() {
    return file.getName();
  }
}
