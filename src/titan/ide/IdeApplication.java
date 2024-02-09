package titan.ide;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import titan.ide.context.IdeContext;
import titan.ide.exception.IdeRuntimeException;
import titan.ide.window.MainWindow;

/**
 * .
 *
 * @author tian wei jun
 */
public class IdeApplication {

  public void run() {
    SwingUtilities.invokeLater(
        new Runnable() {
          @Override
          public void run() {
            IdeContext ideContext = IdeContext.init();
            try {
              // 加载皮肤
              UIManager.setLookAndFeel(new FlatLightLaf());
            } catch (UnsupportedLookAndFeelException e) {
              throw new IdeRuntimeException(e);
            }
            MainWindow mainWindow = new MainWindow("titan-ide");
            ideContext.uiContext.mainWindow = mainWindow;
            mainWindow.openProject();
          }
        });
  }

  public static void main(String[] args) {
    new IdeApplication().run();
  }
}
