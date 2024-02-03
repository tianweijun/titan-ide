package titan.ide.services;

import java.io.File;
import titan.ide.config.IdeConfig;
import titan.ide.config.RootIdeConfig;
import titan.ide.context.IdeContext;
import titan.ide.util.FileUtil;
import titan.ide.util.StringUtils;
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
    RootIdeConfig rootIdeConfig = ideContext.rootIdeConfig;

    String persistentIdeConfigFilePath = rootIdeConfig.ideConfigFilePath;
    if (StringUtils.isBlank(persistentIdeConfigFilePath)) {
      persistentIdeConfigFilePath = rootIdeConfig.getDefaultIdeConfigFilePath();
    }
    IdeConfig ideConfig = ideContext.ideConfig;
    String ideConfigStrJson = Json.toPrettyJson(ideConfig);
    File persistentIdeConfigFile = FileUtil.makeFile(persistentIdeConfigFilePath);
    FileUtil.write(persistentIdeConfigFile, ideConfigStrJson);
  }
}
