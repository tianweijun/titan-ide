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
public class RunJComponent extends JPanel {

  public RunJComponent() {
    super();
    setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));

    JLabel run = new JLabel("RUN");

    JScrollPane jScrollPane = new JScrollPane(run);
    jScrollPane.setBorder(BorderFactory.createEmptyBorder());

    add(jScrollPane);
  }
}
