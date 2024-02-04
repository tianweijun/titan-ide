package titan.ide.window.text;

import java.io.File;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * .
 *
 * @author tian wei jun
 */
public class FileJPopupMenu extends JPopupMenu {
  File file;

  public FileJPopupMenu(File file) {
    this.file = file;
    this.init();
  }

  private void init() {
    JMenuItem showInExplorer = new JMenuItem("Show in Explorer");
    showInExplorer.addActionListener(new ShowInExplorerActionListener(file));
    add(showInExplorer);
  }
}
