package titan.ide.window.mennu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * .
 *
 * @author tian wei jun
 */
public class MenuActionListener implements ActionListener {
  Map<String, ActionListener> listeners = new HashMap<>();

  public MenuActionListener() {
    listeners.put("NewProjectMenuItem", new NewProjectMenuItemListener());
    listeners.put("OpenMenuItem", new OpenMenuItemListener());
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String actionCommandKey = e.getActionCommand();
    ActionListener actionListener = listeners.get(actionCommandKey);
    if (null != actionListener) {
      actionListener.actionPerformed(e);
    }
  }
}
