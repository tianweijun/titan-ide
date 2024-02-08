package titan.ide.window.dialog.settings;

import java.awt.GridBagConstraints;

/**
 * .
 *
 * @author tian wei jun
 */
public class SettingsContentEditor extends ContentEditor {

  public SettingsContentEditor() {
    super();

    int indexOfItems = 0;
    GridBagConstraints gbc = new GridBagConstraints();

    addTitle(gbc, indexOfItems++, "Settings");

    addEmptySpaceFill(gbc, indexOfItems++);
  }
}
