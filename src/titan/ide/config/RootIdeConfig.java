package titan.ide.config;

import java.io.File;

/**
 * .
 *
 * @author tian wei jun
 */
public class RootIdeConfig {
  public String ideConfigFilePath;

  public String getDefaultIdeConfigFilePath() {
    String userHomeFileDirectory = System.getProperty("user.home");
    userHomeFileDirectory =
        userHomeFileDirectory
            + File.separator
            + "titanide"
            + File.separator
            + "titanIdeConfig.json";
    return userHomeFileDirectory;
  }
}
