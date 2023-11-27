package titan.ide.context;

import titan.ide.config.IdeConfig;
import titan.ide.config.ProjectIdeConfig;
import titan.ide.window.MainWindow;

/**
 * context.
 *
 * @author tian wei jun
 */
public class IdeContext {

  private static ThreadLocal<IdeContext> contextThreadLocal = new ThreadLocal<>();

  public ProjectIdeConfig projectIdeConfig;

  public IdeConfig ideConfig;

  public MainWindow mainWindow;

  public static void set(IdeContext ctx) {
    contextThreadLocal.set(ctx);
  }

  public static void clear() {
    contextThreadLocal.remove();
  }

  public static IdeContext get() {
    return contextThreadLocal.get();
  }

  public static IdeContext init() {
    IdeContext ideContext = new IdeContext();
    set(ideContext);
    return ideContext;
  }
}
