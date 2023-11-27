package titan.ide.window;

import java.io.File;
import titan.ide.services.MainWindowService;

/**
 * .
 *
 * @author tian wei jun
 */
public class MainWindow extends AbstractWindow {

  MainWindowService mainWindowService;
  MainWindowViewManager viewManager;

  public MainWindow(String title) {
    super(title);
    mainWindowService = new MainWindowService();
    viewManager = new MainWindowViewManager(this);

    viewManager.init();
  }

  public void open() {
    this.setVisible(true);
  }

  public void open(File file) {
    viewManager.open(file);
  }
}
