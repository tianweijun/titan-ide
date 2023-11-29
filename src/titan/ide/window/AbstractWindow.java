package titan.ide.window;

import java.awt.HeadlessException;
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
}
