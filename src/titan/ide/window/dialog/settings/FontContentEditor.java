package titan.ide.window.dialog.settings;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.InputVerifier;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import titan.ide.config.IdeConfig;
import titan.ide.context.IdeContext;
import titan.ide.context.ui.SettingsChangeAction;
import titan.ide.context.ui.UiContext;

/**
 * .
 *
 * @author tian wei jun
 */
public class FontContentEditor extends ContentEditor {
  JComboBox<String> fontNameJComboBox;
  JTextField fontSizeLabelTextField;

  public FontContentEditor() {
    super();

    int indexOfItems = 0;
    GridBagConstraints gbc = new GridBagConstraints();

    addTitle(gbc, indexOfItems++, "Font");

    indexOfItems = addFontOption(gbc, indexOfItems);

    addEmptySpaceFill(gbc, indexOfItems++);
  }

  private int addFontOption(GridBagConstraints gbc, int gridy) {
    IdeConfig ideConfig = IdeContext.get().ideConfig;
    fontSizeLabelTextField = new JTextField(String.valueOf(ideConfig.fontSizeOfTextEditor));
    getFontJComboBox();

    JLabel fontNameLabel = new JLabel("Font:");
    gbc.gridx = 0;
    gbc.gridy = gridy;
    gbc.weightx = 1.0;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(15, 15, 15, 15);
    add(fontNameLabel, gbc);

    gbc.gridx = 1;
    gbc.gridy = gridy;
    gbc.insets = new Insets(15, 15, 15, 15);
    add(fontNameJComboBox, gbc);
    fontNameJComboBox.setSelectedItem(ideConfig.fontNameOfTextEditor);
    ++gridy;

    JLabel fontSizeLabel = new JLabel("Size:");
    gbc.gridx = 0;
    gbc.gridy = gridy;
    gbc.weightx = 1.0;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(15, 15, 15, 15);
    add(fontSizeLabel, gbc);

    fontSizeLabelTextField.setInputVerifier(
        new InputVerifier() {
          @Override
          public boolean verify(JComponent input) {
            boolean verify;
            try {
              Integer.parseInt(fontSizeLabelTextField.getText());
              verify = true;
            } catch (Exception ex) {
              verify = false;
            }
            return verify;
          }
        });
    fontSizeLabelTextField.addFocusListener(
        new FocusListener() {
          @Override
          public void focusGained(FocusEvent e) {}

          @Override
          public void focusLost(FocusEvent e) {
            doChangeAction();
          }
        });
    fontSizeLabelTextField.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            doChangeAction();
          }
        });
    gbc.gridx = 1;
    gbc.gridy = gridy;
    gbc.insets = new Insets(15, 15, 15, 15);
    add(fontSizeLabelTextField, gbc);
    ++gridy;

    return gridy;
  }

  private JComboBox<String> getFontJComboBox() {
    fontNameJComboBox = new JComboBox<String>();
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    Font[] fonts = ge.getAllFonts();

    for (Font font : fonts) {
      fontNameJComboBox.addItem(font.getFontName());
    }

    fontNameJComboBox.addItemListener(
        new ItemListener() {
          @Override
          public void itemStateChanged(ItemEvent e) {
            doChangeAction();
          }
        });
    return fontNameJComboBox;
  }

  private void doChangeAction() {
    String fontName = (String) fontNameJComboBox.getSelectedItem();
    int fontSize = Integer.parseInt(fontSizeLabelTextField.getText());
    IdeContext ideContext = IdeContext.get();
    IdeConfig ideConfig = ideContext.ideConfig;
    boolean isNotChange =
        fontName.equals(ideConfig.fontNameOfTextEditor)
            && fontSize == ideConfig.fontSizeOfTextEditor;
    if (!isNotChange) {
      UiContext uiContext = ideContext.uiContext;
      uiContext.addSettingsChangeActions(new FontSettingsChangeAction(fontName, fontSize));
      uiContext.applyBtn.setEnabled(true);
      uiContext.applyBtn.requestFocus();
    }
  }

  public static class FontSettingsChangeAction extends SettingsChangeAction {
    String fontName;
    int fontSize;

    public FontSettingsChangeAction(String fontName, int fontSize) {
      super(SettingsChangeActionType.FONT_SETTINGS);
      this.fontName = fontName;
      this.fontSize = fontSize;
    }

    @Override
    public void doChangeAction() {
      IdeContext ideContext = IdeContext.get();
      IdeConfig ideConfig = ideContext.ideConfig;

      ideConfig.fontNameOfTextEditor = fontName;
      ideConfig.fontSizeOfTextEditor = fontSize;

      ideContext.uiContext.mainWindow.viewManager.textEditor.reloadTextPanes();
    }
  }
}
