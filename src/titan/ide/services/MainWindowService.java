package titan.ide.services;

import java.io.File;
import titan.ide.config.JsonConfig;
import titan.ide.config.ProjectIdeConfig;
import titan.ide.config.ProjectIdeConfigBuilder;
import titan.ide.context.IdeContext;
import titan.ide.util.FileUtil;
import titan.json.Json;

/**
 * .
 *
 * @author tian wei jun
 */
public class MainWindowService {

  public void actionOnWindowClosing() {
    persistIdeConfig();
    persistProjectIdeConfig();
    IdeContext.clear();
  }

  private void persistProjectIdeConfig() {
    IdeContext ideContext = IdeContext.get();
    File projectIdeConfigFile = ideContext.projectIdeConfigFile;
    if (projectIdeConfigFile == null) {
      if (ideContext.projectFileDirectory != null) {
        projectIdeConfigFile =
            new File(
                ideContext.projectFileDirectory,
                ProjectIdeConfig.PROJECT_TITAN_IDE_CONFIG_FILE_NAME);
      }
    }
    if (null != projectIdeConfigFile) {
      String strJson = Json.toPrettyJson(ideContext.projectIdeConfig);
      FileUtil.write(projectIdeConfigFile, strJson, JsonConfig.JSON_ENCODING);
    }
  }

  public void persistIdeConfig() {
    IdeContext ideContext = IdeContext.get();

    String persistentFilePath = ideContext.getIdeConfigFilePath();
    String strJson = Json.toPrettyJson(ideContext.ideConfig);
    File persistentFile = FileUtil.makeFile(persistentFilePath);
    FileUtil.write(persistentFile, strJson, JsonConfig.JSON_ENCODING);
  }

  public void updateProjectContext(File file) {
    File projectIdeConfigFile = getProjectIdeConfigFile(file);
    if (null != projectIdeConfigFile) {
      new ProjectIdeConfigBuilder().build(projectIdeConfigFile);
    }
    IdeContext ideContext = IdeContext.get();
    if (file.isDirectory()) {
      ideContext.projectFileDirectory = file;
    }
  }

  private File getProjectIdeConfigFile(File file) {
    if (ProjectIdeConfig.isProjectIdeConfigFile(file)) {
      return file;
    }
    File projectIdeConfigFile = null;
    File[] childrenFile = file.listFiles();
    if (null != childrenFile) {
      for (File childFile : childrenFile) {
        if (ProjectIdeConfig.isProjectIdeConfigFile(childFile)) {
          projectIdeConfigFile = childFile;
          break;
        }
      }
    }

    return projectIdeConfigFile;
  }
}
