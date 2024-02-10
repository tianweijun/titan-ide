package titan.ide.context;

import java.io.File;
import titan.ide.config.IdeConfig;
import titan.ide.config.IdeConfigBuilder;
import titan.ide.config.ProjectIdeConfig;
import titan.ide.config.ProjectIdeConfigBuilder;
import titan.ide.config.RootIdeConfig;
import titan.ide.config.RootIdeConfigBuilder;
import titan.ide.context.ui.UiContext;
import titan.ide.util.StringUtils;
import titan.json.Json;

/**
 * context.
 *
 * @author tian wei jun
 */
public class IdeContext {

  private static ThreadLocal<IdeContext> contextThreadLocal = new ThreadLocal<>();

  public ProjectIdeConfig projectIdeConfig;

  public RootIdeConfig rootIdeConfig;

  public IdeConfig ideConfig;

  public UiContext uiContext;

  public File projectFileDirectory = null;
  public File projectIdeConfigFile = null;

  public static void set(IdeContext ctx) {
    contextThreadLocal.set(ctx);
  }

  public static void clear() {
    contextThreadLocal.remove();
    Json.destruct();
  }

  public static IdeContext get() {
    return contextThreadLocal.get();
  }

  public static IdeContext init() {
    IdeContext ideContext = new IdeContext();
    ideContext.rootIdeConfig = new RootIdeConfigBuilder().build();
    ideContext.ideConfig = new IdeConfigBuilder().build(ideContext.rootIdeConfig);
    ideContext.projectIdeConfig = new ProjectIdeConfigBuilder().build();
    ideContext.uiContext = new UiContext();
    set(ideContext);
    return ideContext;
  }

  public String getFileEncoding() {
    String fileEncoding = projectIdeConfig.fileEncoding;
    if (StringUtils.isBlank(fileEncoding)) {
      fileEncoding = ideConfig.fileEncoding;
    }
    return fileEncoding;
  }

  public String getIdeConfigFilePath() {
    String ideConfigFilePath = rootIdeConfig.ideConfigFilePath;
    if (StringUtils.isBlank(ideConfigFilePath)) {
      ideConfigFilePath = rootIdeConfig.getDefaultIdeConfigFilePath();
    }
    return ideConfigFilePath;
  }
}
