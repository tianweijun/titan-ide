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
            enableAntiAliasing();
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

  // 全局字体抗锯齿，必须在初始化 JFrame 之前调用！
  static void enableAntiAliasing() {
    System.setProperty("awt.useSystemAAFontSettings", "on");
    System.setProperty("swing.aatext", "true");
  }

  public static void main(String[] args) {
    new IdeApplication().run();
  }
}
