package titan.ide.config;

import titan.ide.utli.StringUtils;

/**
 * .
 *
 * @author tian wei jun
 */
public class IdeConfig {
  public String lastOpenedFileDirectory;

  public IdeConfig toPersistentObject() {
    IdeConfig ideConfig = new IdeConfig();
    if (StringUtils.isNotBlank(lastOpenedFileDirectory)) {
      ideConfig.lastOpenedFileDirectory = lastOpenedFileDirectory.replace("\\", "\\\\");
    }
    return ideConfig;
  }
}
