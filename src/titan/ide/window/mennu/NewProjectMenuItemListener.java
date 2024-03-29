package titan.ide.window.mennu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import titan.ide.context.IdeContext;
import titan.ide.window.dialog.newproject.NewProjectDialog;

/**
 * .
 *
 * @author tian wei jun
 */
public class NewProjectMenuItemListener implements ActionListener {

  @Override
  public void actionPerformed(ActionEvent e) {
    new NewProjectDialog(IdeContext.get().uiContext.mainWindow, "New Project", true);
  }
}
