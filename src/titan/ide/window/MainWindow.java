package titan.ide.window;

import java.io.File;

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

  public void open() {
    this.setVisible(true);
  }

  public void open(File file) {
    viewManager.open(file);
  }
}
