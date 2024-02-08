package titan.ide.window.mennu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import titan.ide.context.IdeContext;
import titan.ide.window.dialog.settings.SettingsDialog;

/**
 * .
 *
 * @author tian wei jun
 */
public class SettingsMenuItemListener implements ActionListener {

  @Override
  public void actionPerformed(ActionEvent e) {
    new SettingsDialog(IdeContext.get().uiContext.mainWindow, "Settings", true);
  }
}
