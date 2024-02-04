package titan.ide.window.text;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import titan.ide.exception.IdeRuntimeException;

/**
 * .
 *
 * @author tian wei jun
 */
public class ShowInExplorerActionListener implements ActionListener {
  File file;

  public ShowInExplorerActionListener(File file) {
    this.file = file;
  }

  @Override
  public void actionPerformed(ActionEvent e) {

    try {
      Desktop desktop = Desktop.getDesktop();
      desktop.open(file.getParentFile());
    } catch (IOException ex) {
      throw new IdeRuntimeException(ex);
    }
  }
}
