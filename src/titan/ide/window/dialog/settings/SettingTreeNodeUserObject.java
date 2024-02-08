package titan.ide.window.dialog.settings;

import javax.swing.JComponent;

/**
 * .
 *
 * @author tian wei jun
 */
public class SettingTreeNodeUserObject {
  public JComponent editJComponent;

  public String name;

  public SettingTreeNodeUserObject(String name, JComponent editJComponent) {
    this.name = name;
    this.editJComponent = editJComponent;
  }

  @Override
  public String toString() {
    return name;
  }
}
