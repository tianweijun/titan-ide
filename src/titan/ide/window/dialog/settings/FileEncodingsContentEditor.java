package titan.ide.window.dialog.settings;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import titan.ide.config.IdeConfig;
import titan.ide.config.ProjectIdeConfig;
import titan.ide.context.IdeContext;
import titan.ide.context.ui.SettingsChangeAction;
import titan.ide.context.ui.UiContext;
import titan.ide.util.StringUtils;

/**
 * .
 *
 * @author tian wei jun
 */
public class FileEncodingsContentEditor extends ContentEditor {
  JComboBox<String> globalEncodingJComboBox;
  JComboBox<String> projectEncodingJComboBox;

  public FileEncodingsContentEditor() {
    super();

    int indexOfItems = 0;
    GridBagConstraints gbc = new GridBagConstraints();

    addTitle(gbc, indexOfItems++, "File Encodings");

    indexOfItems = addFileEncodingsOption(gbc, indexOfItems);

    addEmptySpaceFill(gbc, indexOfItems++);
  }

  private int addFileEncodingsOption(GridBagConstraints gbc, int gridy) {
    IdeContext ideContext = IdeContext.get();
    getGlobalEncodingJComboBox();
    getProjectEncodingJComboBox();

    JLabel globalEncodingLabel = new JLabel("Global Encoding:");
    gbc.gridx = 0;
    gbc.gridy = gridy;
    gbc.weightx = 1.0;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(15, 15, 15, 15);
    add(globalEncodingLabel, gbc);

    gbc.gridx = 1;
    gbc.gridy = gridy;
    gbc.insets = new Insets(15, 15, 15, 15);
    add(globalEncodingJComboBox, gbc);
    globalEncodingJComboBox.setSelectedItem(ideContext.ideConfig.fileEncoding);
    ++gridy;

    JLabel projectEncodingLabel = new JLabel("Project Encoding:");
    gbc.gridx = 0;
    gbc.gridy = gridy;
    gbc.weightx = 1.0;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(15, 15, 15, 15);
    add(projectEncodingLabel, gbc);

    gbc.gridx = 1;
    gbc.gridy = gridy;
    gbc.insets = new Insets(15, 15, 15, 15);
    add(projectEncodingJComboBox, gbc);
    projectEncodingJComboBox.setSelectedItem(ideContext.projectIdeConfig.fileEncoding);
    ++gridy;

    return gridy;
  }

  private JComboBox<String> getProjectEncodingJComboBox() {
    projectEncodingJComboBox = new JComboBox<String>();

    for (String encodingName : getEncodingNames()) {
      projectEncodingJComboBox.addItem(encodingName);
    }

    projectEncodingJComboBox.addItemListener(
        new ItemListener() {
          @Override
          public void itemStateChanged(ItemEvent e) {
            doChangeAction();
          }
        });
    return projectEncodingJComboBox;
  }

  private JComboBox<String> getGlobalEncodingJComboBox() {
    globalEncodingJComboBox = new JComboBox<String>();

    for (String encodingName : getEncodingNames()) {
      globalEncodingJComboBox.addItem(encodingName);
    }

    globalEncodingJComboBox.addItemListener(
        new ItemListener() {
          @Override
          public void itemStateChanged(ItemEvent e) {
            doChangeAction();
          }
        });
    return globalEncodingJComboBox;
  }

  private LinkedHashSet<String> getEncodingNames() {
    LinkedList<String> defaultEncodingNames = new LinkedList<>();
    defaultEncodingNames.add("");
    defaultEncodingNames.add("UTF-8");
    defaultEncodingNames.add("GBK");
    defaultEncodingNames.add("ISO-8859-1");
    defaultEncodingNames.add("UTF-16");

    IdeContext ideContext = IdeContext.get();
    if (StringUtils.isNotBlank(ideContext.ideConfig.fileEncoding)) {
      defaultEncodingNames.addFirst(ideContext.ideConfig.fileEncoding);
    }
    if (StringUtils.isNotBlank(ideContext.projectIdeConfig.fileEncoding)) {
      defaultEncodingNames.addFirst(ideContext.projectIdeConfig.fileEncoding);
    }

    LinkedHashSet<String> encodingNames = new LinkedHashSet<>(defaultEncodingNames.size());
    encodingNames.addAll(defaultEncodingNames);
    return encodingNames;
  }

  private void doChangeAction() {
    String globalEncoding = (String) globalEncodingJComboBox.getSelectedItem();
    String projectEncoding = (String) projectEncodingJComboBox.getSelectedItem();
    IdeContext ideContext = IdeContext.get();
    IdeConfig ideConfig = ideContext.ideConfig;
    ProjectIdeConfig projectIdeConfig = ideContext.projectIdeConfig;
    boolean isChange = false;
    if (StringUtils.isBlank(globalEncoding)) {
      globalEncoding = ideConfig.fileEncoding;
    } else {
      if (!globalEncoding.equals(ideConfig.fileEncoding)) {
        isChange = true;
      }
    }
    if (StringUtils.isBlank(projectEncoding)) {
      projectEncoding = projectIdeConfig.fileEncoding;
    } else {
      if (!projectEncoding.equals(projectIdeConfig.fileEncoding)) {
        isChange = true;
      }
    }

    if (isChange) {
      UiContext uiContext = ideContext.uiContext;
      uiContext.addSettingsChangeActions(
          new FileEncodingsSettingsChangeAction(globalEncoding, projectEncoding));
      uiContext.applyBtn.setEnabled(true);
      uiContext.applyBtn.requestFocus();
    }
  }

  public static class FileEncodingsSettingsChangeAction extends SettingsChangeAction {
    String globalEncoding;
    String projectEncoding;

    public FileEncodingsSettingsChangeAction(String globalEncoding, String projectEncoding) {
      super(SettingsChangeActionType.FILE_ENCODINGS_SETTINGS);
      this.globalEncoding = globalEncoding;
      this.projectEncoding = projectEncoding;
    }

    @Override
    public void doChangeAction() {
      IdeContext ideContext = IdeContext.get();

      ideContext.ideConfig.fileEncoding = globalEncoding;
      ideContext.projectIdeConfig.fileEncoding = projectEncoding;

      ideContext.uiContext.mainWindow.viewManager.textEditor.reloadTextPanes();
    }
  }
}
