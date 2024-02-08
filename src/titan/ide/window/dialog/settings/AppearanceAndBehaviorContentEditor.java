package titan.ide.window.dialog.settings;

import java.awt.GridBagConstraints;

/**
 * .
 *
 * @author tian wei jun
 */
public class AppearanceAndBehaviorContentEditor extends ContentEditor {

  public AppearanceAndBehaviorContentEditor() {
    super();

    int indexOfItems = 0;
    GridBagConstraints gbc = new GridBagConstraints();

    addTitle(gbc, indexOfItems++, "Appearance & Behavior");

    addEmptySpaceFill(gbc, indexOfItems++);
  }
}
