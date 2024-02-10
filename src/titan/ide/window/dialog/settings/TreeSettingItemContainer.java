package titan.ide.window.dialog.settings;

import com.formdev.flatlaf.ui.FlatTreeUI;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import titan.ide.context.IdeContext;

/**
 * .
 *
 * @author tian wei jun
 */
public class TreeSettingItemContainer extends JScrollPane {
  JSplitPane centerPanel;
  JTree tree;

  public TreeSettingItemContainer(JSplitPane centerPanel) {
    super();

    this.centerPanel = centerPanel;
    setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY));

    tree = new JTree();
    tree.setBackground(new Color(0xE6EBF0));
    tree.setFont(new Font(null, Font.PLAIN, 16));
    tree.setRowHeight(24);
    FlatTreeUI treeUi = (FlatTreeUI) tree.getUI();
    treeUi.setCollapsedIcon(new ImageIcon(this.getClass().getResource("/img/triangle-right.png")));
    treeUi.setExpandedIcon(new ImageIcon(this.getClass().getResource("/img/triangle-down.png")));

    tree.addTreeSelectionListener(
        new TreeSelectionListener() {
          @Override
          public void valueChanged(TreeSelectionEvent e) {
            TreePath path = e.getPath();
            DefaultMutableTreeNode lastPathComponent =
                (DefaultMutableTreeNode) path.getLastPathComponent();
            JComponent editJComponent =
                ((SettingTreeNodeUserObject) lastPathComponent.getUserObject()).editJComponent;
            // action
            int dividerLocation = centerPanel.getDividerLocation();
            centerPanel.setDividerLocation(dividerLocation);
            centerPanel.setRightComponent(editJComponent);
            centerPanel.invalidate();
            centerPanel.repaint();
          }
        });

    DefaultMutableTreeNode root = getRootTree();
    tree.setModel(new DefaultTreeModel(root));

    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBackground(tree.getBackground());

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 1.0;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    panel.add(tree, gbc);

    JPanel emptySpaceFill = new JPanel();
    emptySpaceFill.setOpaque(false);
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weightx = 1.0;
    gbc.weighty = 1.0;
    gbc.fill = GridBagConstraints.BOTH;
    panel.add(emptySpaceFill, gbc);

    setViewportView(panel);

    IdeContext.get().uiContext.settingsTree = tree;
  }

  private DefaultMutableTreeNode getRootTree() {
    DefaultMutableTreeNode root =
        new DefaultMutableTreeNode(
            new SettingTreeNodeUserObject("Settings", new SettingsContentEditor()));
    root.add(getAppearanceAndBehaviorTree());
    root.add(getEditorTree());
    return root;
  }

  private MutableTreeNode getAppearanceAndBehaviorTree() {
    DefaultMutableTreeNode appearanceAndBehavior =
        new DefaultMutableTreeNode(
            new SettingTreeNodeUserObject(
                "Appearance & Behavior", new AppearanceAndBehaviorContentEditor()));
    return appearanceAndBehavior;
  }

  private MutableTreeNode getEditorTree() {
    DefaultMutableTreeNode editor =
        new DefaultMutableTreeNode(
            new SettingTreeNodeUserObject("Editor", new EditorContentEditor()));
    editor.add(
        new DefaultMutableTreeNode(new SettingTreeNodeUserObject("Font", new FontContentEditor())));
    editor.add(
        new DefaultMutableTreeNode(
            new SettingTreeNodeUserObject("File Encodings", new FileEncodingsContentEditor())));
    return editor;
  }
}
