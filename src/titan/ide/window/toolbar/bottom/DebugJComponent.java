package titan.ide.window.toolbar.bottom;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * .
 *
 * @author tian wei jun
 */
public class DebugJComponent extends JPanel {

  public DebugJComponent() {
    super();

    setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));

    JLabel debug = new JLabel("DEBUG");

    JScrollPane jScrollPane = new JScrollPane(debug);
    jScrollPane.setBorder(BorderFactory.createEmptyBorder());

    add(jScrollPane);
  }
}
