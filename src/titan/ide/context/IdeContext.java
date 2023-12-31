package titan.ide.context;

import titan.ide.config.IdeConfig;
import titan.ide.config.IdeConfigBuilder;
import titan.ide.config.ProjectIdeConfig;
import titan.ide.config.RootIdeConfig;
import titan.ide.config.RootIdeConfigBuilder;
import titan.ide.window.MainWindow;
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

  public MainWindow mainWindow;

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
    set(ideContext);
    return ideContext;
  }
}
