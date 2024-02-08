package titan.ide.window.dialog.settings;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import titan.ide.context.IdeContext;
import titan.ide.context.ui.SettingsChangeAction;

/**
 * .
 *
 * @author tian wei jun
 */
public class SettingsDialog extends JDialog {
  JComponent settingContentContainer;

  // ---btnsPanel start---
  private JButton okBtn = new JButton("ok");
  private JButton cancelBtn = new JButton("cancel");
  private JButton applyBtn = new JButton("apply");
  // ---btnsPanel end---

  public SettingsDialog(Frame owner, String title, boolean modal) {
    super(owner, title, modal);
    setSize(950, 700);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setResizable(false);
    setLayout(new BorderLayout());

    applyBtn.setEnabled(false);
    IdeContext.get().uiContext.applyBtn = applyBtn;

    add(createCenterPanel(), BorderLayout.CENTER);
    add(createBtnsPanel(), BorderLayout.SOUTH);

    setLocationRelativeTo(null);
    setVisible(true);
  }

  private JSplitPane createCenterPanel() {
    JSplitPane centerPanel = new JSplitPane();

    centerPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.GRAY));
    centerPanel.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
    centerPanel.setDividerLocation(240);
    centerPanel.setRightComponent(initSettingContentContainer());
    centerPanel.setLeftComponent(initTreeSettingItemContainer(centerPanel));

    return centerPanel;
  }

  private Component initSettingContentContainer() {
    settingContentContainer = new JPanel();
    return settingContentContainer;
  }

  private Component initTreeSettingItemContainer(JSplitPane centerPanel) {
    return new TreeSettingItemContainer(centerPanel);
  }

  private Component createBtnsPanel() {
    okBtn.setBackground(new Color(0x4A86C7));

    JPanel btnsPanel = new JPanel();
    btnsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 5));
    btnsPanel.add(okBtn);
    btnsPanel.add(cancelBtn);
    btnsPanel.add(applyBtn);

    JDialog self = this;
    cancelBtn.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            IdeContext.get().uiContext.settingsChangeActions.clear();
            self.dispose();
          }
        });
    okBtn.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            for (SettingsChangeAction action : IdeContext.get().uiContext.settingsChangeActions) {
              action.doChangeAction();
            }
            self.dispose();
          }
        });
    applyBtn.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            for (SettingsChangeAction action : IdeContext.get().uiContext.settingsChangeActions) {
              action.doChangeAction();
            }
            applyBtn.setEnabled(false);
          }
        });
    return btnsPanel;
  }
}
