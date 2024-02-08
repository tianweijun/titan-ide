package titan.ide.window.statusbar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * .
 *
 * @author tian wei jun
 */
public class StatusBar extends JPanel {

  public JPanel jpanelInStatusBarLeft;
  public JPanel jpanelInStatusBarRight;

  public StatusBar() {
    setLayout(new BorderLayout());
    setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY));
    jpanelInStatusBarLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jpanelInStatusBarRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    add(jpanelInStatusBarLeft, BorderLayout.WEST);
    add(jpanelInStatusBarRight, BorderLayout.EAST);

    addMsgToLeftStatusBar("All files are up-to-date (moments ago)");
    addMsgToRightStatusBar("UTF-8");
  }

  private void addMsgToRightStatusBar(String msg) {
    JLabel msgLabel = new JLabel(msg);
    msgLabel.setForeground(Color.BLUE);
    jpanelInStatusBarRight.add(msgLabel);
  }

  private void addMsgToLeftStatusBar(String msg) {
    JLabel msgLabel = new JLabel(msg);
    jpanelInStatusBarLeft.add(msgLabel);
  }
}
