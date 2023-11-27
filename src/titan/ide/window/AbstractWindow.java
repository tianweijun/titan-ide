package titan.ide.window;

import java.awt.HeadlessException;
import java.awt.Point;
import javax.swing.JFrame;

/**
 * .
 *
 * @author tian wei jun
 */
public abstract class AbstractWindow extends JFrame {

  public AbstractWindow() throws HeadlessException {}

  public AbstractWindow(String title) throws HeadlessException {
    super(title);
  }

  /**
   * 传入外部长宽与窗口（内部）长宽，计算中心位置，用于窗口居中位置计算
   *
   * @param outerWidth 外部宽度（如屏幕宽度）
   * @param outerHeight 外部高度（如屏幕高度）
   * @param innerWidth 放置的宽度（如容器宽度）
   * @param innerHeight 放置的高度（如容器高度）
   * @return 居中位置
   */
  protected Point calculateCenter(
      double outerWidth, double outerHeight, double innerWidth, double innerHeight) {
    int x = (int) ((outerWidth - innerWidth) / 2);
    int y = (int) ((outerHeight - innerHeight) / 2);
    return new Point(x, y);
  }
}
