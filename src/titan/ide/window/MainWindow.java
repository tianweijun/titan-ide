package titan.ide.window;

import java.io.File;
import titan.ide.context.IdeContext;
import titan.ide.util.StringUtils;

/**
 * .
 *
 * @author tian wei jun
 */
public class MainWindow extends AbstractWindow {

  public MainWindowViewManager viewManager;

  public MainWindow(String title) {
    super(title);
    viewManager = new MainWindowViewManager(this);

    viewManager.init();
  }

  public void openProject() {
    IdeContext ideContext = IdeContext.get();
    String lastOpenedFileDirectory = ideContext.ideConfig.lastOpenedFileDirectory;
    if (StringUtils.isNotBlank(lastOpenedFileDirectory)) {
      openProject(new File(lastOpenedFileDirectory));
    }
    this.setVisible(true);
  }

  public void openProject(File file) {
    viewManager.open(file);
  }

  public void actionOnWindowClosing() {
    viewManager.actionOnWindowClosing();
  }
}
