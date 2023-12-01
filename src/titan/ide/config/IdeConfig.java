package titan.ide.config;

import titan.ide.utli.StringUtils;

/**
 * .
 *
 * @author tian wei jun
 */
public class IdeConfig {
  public String lastOpenedFileDirectory;

  public void beOverrode(IdeConfig ideConfigInFile) {
    if (StringUtils.isNotBlank(ideConfigInFile.lastOpenedFileDirectory)) {
      this.lastOpenedFileDirectory = ideConfigInFile.lastOpenedFileDirectory;
    }
  }
}
