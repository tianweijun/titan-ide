package titan.ide.window.dialog.settings;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.Enumeration;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import titan.ide.context.IdeContext;

/**
 * .
 *
 * @author tian wei jun
 */
public class EditorContentEditor extends ContentEditor {

  public EditorContentEditor() {
    super();

    int indexOfItems = 0;
    GridBagConstraints gbc = new GridBagConstraints();

    addTitle(gbc, indexOfItems++, "Editor");

    gbc.gridx = 0;
    gbc.gridy = indexOfItems++;
    gbc.weightx = 1.0;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    add(getIntroduction(), gbc);

    gbc.gridx = 0;
    gbc.gridy = indexOfItems++;
    gbc.weightx = 1.0;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    add(addHyperlinkPane(), gbc);

    addEmptySpaceFill(gbc, indexOfItems++);
  }

  private Component addHyperlinkPane() {
    JPanel jPanel = new JPanel(new GridBagLayout());
    jPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

    GridBagConstraints gbc = new GridBagConstraints();
    int indexOfItems = 0;

    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 1.0;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(3, 15, 3, 5);

    addHyperlink(jPanel, "Font", gbc, indexOfItems++);
    addHyperlink(jPanel, "File Encodings", gbc, indexOfItems++);

    return jPanel;
  }

  private void addHyperlink(JPanel jPanel, String link, GridBagConstraints gbc, int gridy) {
    gbc.gridy = gridy;
    jPanel.add(getHyperlink(link), gbc);
  }

  private Component getHyperlink(String link) {
    JLabel linkComponent = new JLabel(link);
    linkComponent.setForeground(Color.BLUE);

    linkComponent.addMouseListener(
        new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            JTree settingsTree = IdeContext.get().uiContext.settingsTree;
            DefaultMutableTreeNode root =
                (DefaultMutableTreeNode) settingsTree.getModel().getRoot();
            DefaultMutableTreeNode editor = getChildByName(root, "Editor");
            DefaultMutableTreeNode linkNode = getChildByName(editor, link);
            TreePath treePath = new TreePath(new DefaultMutableTreeNode[] {root, editor, linkNode});
            settingsTree.setSelectionPath(treePath);
          }

          private DefaultMutableTreeNode getChildByName(
              DefaultMutableTreeNode parent, String name) {
            DefaultMutableTreeNode node = null;
            Enumeration children = parent.children();
            while (children.hasMoreElements()) {
              Object child = children.nextElement();
              if (name.equals(child.toString())) {
                node = (DefaultMutableTreeNode) child;
                break;
              }
            }
            return node;
          }

          @Override
          public void mouseEntered(MouseEvent e) {
            JLabel label = ((JLabel) e.getSource());
            label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            // underline
            Font originalFont = label.getFont();
            Map<TextAttribute, Object> attributes =
                (Map<TextAttribute, Object>) originalFont.getAttributes();
            attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            label.setFont(originalFont.deriveFont(attributes));
          }

          @Override
          public void mouseExited(MouseEvent e) {
            JLabel label = ((JLabel) e.getSource());
            label.setCursor(Cursor.getDefaultCursor());
            // no underline
            Font originalFont = label.getFont();
            Map<TextAttribute, Object> attributes =
                (Map<TextAttribute, Object>) originalFont.getAttributes();
            attributes.put(TextAttribute.UNDERLINE, -1);
            label.setFont(originalFont.deriveFont(attributes));
          }
        });

    return linkComponent;
  }

  private Component getIntroduction() {
    JTextArea introduction =
        new JTextArea(
            "Personalize source code appearance by changing fonts, highlighting styles, indents, etc. Customize the Editor from line numbers, caret placement and tabs to source code inspections, setting up templates and file encodings.");
    introduction.setLineWrap(true);
    introduction.setWrapStyleWord(true);
    introduction.setEditable(false);
    return introduction;
  }
}
