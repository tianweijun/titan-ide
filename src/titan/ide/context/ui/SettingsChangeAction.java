package titan.ide.context.ui;

import java.util.Objects;

/**
 * .
 *
 * @author tian wei jun
 */
public abstract class SettingsChangeAction {
  public SettingsChangeActionType actionType;

  public SettingsChangeAction(SettingsChangeActionType actionType) {
    this.actionType = actionType;
  }

  public abstract void doChangeAction();

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SettingsChangeAction action = (SettingsChangeAction) o;
    return actionType == action.actionType;
  }

  @Override
  public int hashCode() {
    return Objects.hash(actionType);
  }

  public static enum SettingsChangeActionType {
    FONT_SETTINGS
  }
}
