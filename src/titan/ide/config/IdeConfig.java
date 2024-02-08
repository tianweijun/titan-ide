package titan.ide.config;

import java.awt.Font;
import titan.ide.util.StringUtils;

/**
 * .
 *
 * @author tian wei jun
 */
public class IdeConfig {
  public String lastOpenedFileDirectory;
  public String fontNameOfTextEditor = new Font(null).getFontName();
  public int fontSizeOfTextEditor = 24;

  public void beOverrode(IdeConfig ideConfigInFile) {
    if (StringUtils.isNotBlank(ideConfigInFile.lastOpenedFileDirectory)) {
      this.lastOpenedFileDirectory = ideConfigInFile.lastOpenedFileDirectory;
    }
    this.fontNameOfTextEditor = ideConfigInFile.fontNameOfTextEditor;
    this.fontSizeOfTextEditor = ideConfigInFile.fontSizeOfTextEditor;
  }
}
