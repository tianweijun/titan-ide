package titan.ide.services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import titan.ide.config.IdeConfig;
import titan.ide.config.RootIdeCfg;
import titan.ide.context.IdeContext;
import titan.ide.exception.IdeRuntimeException;
import titan.ide.utli.FileUtil;
import titan.ide.utli.StringUtils;
import titan.json.Json;

/**
 * .
 *
 * @author tian wei jun
 */
public class MainWindowService {

  public void actionOnWindowClosing() {
    persistIdeConfig();
    IdeContext.clear();
  }

  public void persistIdeConfig() {
    IdeContext ideContext = IdeContext.get();
    RootIdeCfg rootIdeCfg = ideContext.rootIdeCfg;

    String persistentIdeConfigFilePath = rootIdeCfg.ideConfigFilePath;
    if (StringUtils.isBlank(persistentIdeConfigFilePath)) {
      persistentIdeConfigFilePath = rootIdeCfg.getDefaultIdeConfigFilePath();
    }
    IdeConfig ideConfig = ideContext.ideConfig;
    String ideConfigStrJson = Json.toPrettyJson(ideConfig);
    File persistentIdeConfigFile = FileUtil.makeFile(persistentIdeConfigFilePath);
    try (FileWriter fileWriter = new FileWriter(persistentIdeConfigFile)) {
      fileWriter.write(ideConfigStrJson);
    } catch (IOException e) {
      throw new IdeRuntimeException(e);
    }
  }
}
