package titan.ide.window.dialog.newproject;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import titan.ide.util.StringUtils;

/**
 * .
 *
 * @author tian wei jun
 */
public class NewProjectDialog extends JDialog {

  // ---projectInfoPanel start----
  private JLabel projectNameLabel = new JLabel("project name");
  private JTextField projectNameTextField = new JTextField(16);

  private JLabel projectLocationLabel = new JLabel("project location");
  private JTextField projectLocationTextField = new JTextField(16);
  // ---projectInfoPanel end----
  // ---btnsPanel start---
  private JButton finishBtn = new JButton("finish");
  private JButton cancelBtn = new JButton("cancel");
  // ---btnsPanel end---

  public NewProjectDialog(Frame owner, String title, boolean modal) {
    super(owner, title, modal);
    setSize(700, 800);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setResizable(false);

    setLayout(new BorderLayout());
    add(createCenterPanel(), BorderLayout.CENTER);
    add(createBtnsPanel(), BorderLayout.SOUTH);

    setLocationRelativeTo(null);
    setVisible(true);
  }

  private JPanel createCenterPanel() {
    JPanel centerPanel = new JPanel();
    centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
    centerPanel.add(createProjectInfoPanel());
    // centerPanel.add(Box.createVerticalGlue());
    return centerPanel;
  }

  private Component createBtnsPanel() {
    JPanel btnsPanel = new JPanel();
    btnsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
    btnsPanel.add(finishBtn);
    btnsPanel.add(cancelBtn);

    JDialog self = this;
    cancelBtn.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            self.dispose();
          }
        });
    finishBtn.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            NewProjectInputVerifier newProjectInputVerifier = new NewProjectInputVerifier();
            boolean valid =
                newProjectInputVerifier.verify(projectNameTextField)
                    && newProjectInputVerifier.verify(projectLocationTextField);
            if (!valid) {
              JOptionPane.showMessageDialog(
                  self,
                  "field is invalid.Please correct it.\n",
                  "Invalid Input",
                  JOptionPane.ERROR_MESSAGE);
            } else {
              self.dispose();
            }
          }
        });
    return btnsPanel;
  }

  private JPanel createProjectInfoPanel() {
    /*
    projectNameTextField.setName(projectNameLabel.getText());
    projectLocationTextField.setName(projectLocationLabel.getText());
    NewProjectInputVerifier newProjectInputVerifier = new NewProjectInputVerifier();
    projectNameTextField.setInputVerifier(newProjectInputVerifier);
    projectLocationTextField.setInputVerifier(newProjectInputVerifier);
    finishBtn.setVerifyInputWhenFocusTarget(true);
    cancelBtn.setVerifyInputWhenFocusTarget(false);*/

    JPanel projectInfoPanel = new JPanel();
    GroupLayout layout = new GroupLayout(projectInfoPanel);
    projectInfoPanel.setLayout(layout);
    layout.setAutoCreateGaps(true);
    layout.setAutoCreateContainerGaps(true);
    layout.setHorizontalGroup(
        layout
            .createSequentialGroup()
            .addGroup(
                layout
                    .createParallelGroup(Alignment.TRAILING)
                    .addComponent(projectNameLabel)
                    .addComponent(projectLocationLabel))
            .addGroup(
                layout
                    .createParallelGroup(Alignment.LEADING)
                    .addComponent(projectNameTextField)
                    .addComponent(projectLocationTextField)));
    layout.setVerticalGroup(
        layout
            .createSequentialGroup()
            .addGroup(
                layout
                    .createParallelGroup(Alignment.BASELINE)
                    .addComponent(projectNameLabel)
                    .addComponent(projectNameTextField))
            .addGroup(
                layout
                    .createParallelGroup(Alignment.BASELINE)
                    .addComponent(projectLocationLabel)
                    .addComponent(projectLocationTextField)));
    return projectInfoPanel;
  }

  static class NewProjectInputVerifier extends InputVerifier {

    @Override
    public boolean verify(JComponent input) {
      JTextField textField = (JTextField) input;
      boolean valid = true;
      String text = textField.getText();
      if (StringUtils.isBlank(text)) {
        valid = false;
      }
      return valid;
    }
  }
}
