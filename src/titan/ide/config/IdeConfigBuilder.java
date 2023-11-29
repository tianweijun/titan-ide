package titan.ide.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import titan.ide.exception.IdeRuntimeException;
import titan.ide.utli.StringUtils;
import titan.json.Json;

/**
 * .
 *
 * @author tian wei jun
 */
public class IdeConfigBuilder {

  public IdeConfig build(RootIdeCfg rootIdeCfg) {
    if (StringUtils.isNotBlank(rootIdeCfg.ideConfigFilePath)) {
      return buildByFilePath(rootIdeCfg.ideConfigFilePath);
    } else {
      return buildByFilePath(rootIdeCfg.getDefaultIdeConfigFilePath());
    }
  }

  private IdeConfig buildByFilePath(String ideConfigFilePath) {
    File cfgFile = new File(ideConfigFilePath);
    if (cfgFile.exists() && cfgFile.isFile()) {
      try (FileInputStream jsonIdeConfigInputStream = new FileInputStream(ideConfigFilePath)) {
        return Json.fromJson(jsonIdeConfigInputStream, IdeConfig.class);
      } catch (IOException e) {
        throw new IdeRuntimeException(e);
      }
    }
    return build();
  }

  private IdeConfig build() {
    return new IdeConfig();
  }
}
