package titan.ide.window.dialog.settings;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * .
 *
 * @author tian wei jun
 */
public abstract class ContentEditor extends JPanel {

  public ContentEditor() {
    super();
    setLayout(new GridBagLayout());
  }

  protected void addTitle(GridBagConstraints gbc, int gridy, String title) {
    gbc.gridx = 0;
    gbc.gridy = gridy;
    gbc.weightx = 1.0;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    add(getTitle(title), gbc);
  }

  protected Component getTitle(String title) {
    return new JLabel("<html><b>" + title + "</b></html>");
  }

  protected void addEmptySpaceFill(GridBagConstraints gbc, int gridy) {
    JPanel emptySpaceFill = new JPanel();
    emptySpaceFill.setOpaque(false);
    gbc.gridx = 0;
    gbc.gridy = gridy;
    gbc.weightx = 1.0;
    gbc.weighty = 1.0;
    gbc.fill = GridBagConstraints.BOTH;
    add(emptySpaceFill, gbc);
  }
}
