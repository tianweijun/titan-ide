package titan.ide.window.mennu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import titan.ide.context.IdeContext;
import titan.ide.window.dialog.NewProjectDialog;

/**
 * .
 *
 * @author tian wei jun
 */
public class NewProjectMenuItemListener implements ActionListener {

  @Override
  public void actionPerformed(ActionEvent e) {
    new NewProjectDialog(IdeContext.get().mainWindow, "New Project", true);
  }
}
