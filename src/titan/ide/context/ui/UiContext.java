package titan.ide.context.ui;

import java.util.HashSet;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JTree;
import titan.ide.window.MainWindow;

/**
 * .
 *
 * @author tian wei jun
 */
public class UiContext {
  public MainWindow mainWindow;
  public JTree settingsTree;
  public JButton applyBtn;

  public Set<SettingsChangeAction> settingsChangeActions = new HashSet<>();

  public void addSettingsChangeActions(SettingsChangeAction settingsChangeAction) {
    if (settingsChangeActions.contains(settingsChangeAction)) {
      settingsChangeActions.remove(settingsChangeAction);
    }
    settingsChangeActions.add(settingsChangeAction);
  }
}
