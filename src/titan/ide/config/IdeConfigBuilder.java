package titan.ide.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import titan.ide.exception.IdeRuntimeException;
import titan.ide.util.StringUtils;
import titan.json.Json;

/**
 * .
 *
 * @author tian wei jun
 */
public class IdeConfigBuilder {

  public IdeConfig build(RootIdeConfig rootIdeConfig) {
    if (StringUtils.isNotBlank(rootIdeConfig.ideConfigFilePath)) {
      return buildByFilePath(rootIdeConfig.ideConfigFilePath);
    } else {
      return buildByFilePath(rootIdeConfig.getDefaultIdeConfigFilePath());
    }
  }

  private IdeConfig build() {
    InputStream jsonIdeConfigInputStream =
        this.getClass().getClassLoader().getResourceAsStream("config/defaultIdeConfig.json");
    return Json.fromJson(jsonIdeConfigInputStream, IdeConfig.class, JsonConfig.JSON_ENCODING);
  }

  private IdeConfig buildByFilePath(String ideConfigFilePath) {
    IdeConfig ideConfig = build();
    File cfgFile = new File(ideConfigFilePath);
    if (cfgFile.exists() && cfgFile.isFile()) {
      try (FileInputStream ideConfigInFileJsonInputStream = new FileInputStream(cfgFile)) {
        IdeConfig ideConfigInFile =
            Json.fromJson(
                ideConfigInFileJsonInputStream, IdeConfig.class, JsonConfig.JSON_ENCODING);
        ideConfig.beOverrode(ideConfigInFile);
      } catch (IOException e) {
        throw new IdeRuntimeException(e);
      }
    }
    return ideConfig;
  }
}
