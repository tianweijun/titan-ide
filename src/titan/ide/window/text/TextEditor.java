package titan.ide.window.text;

import com.formdev.flatlaf.ui.FlatTreeUI;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.LinkedList;
import java.util.Optional;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import titan.ide.context.IdeContext;
import titan.ide.util.FileUtil;

/**
 * .
 *
 * @author tian wei jun
 */
public class TextEditor {
  public JScrollPane projectPane;
  public JTabbedPane textEditorTabbedPane = new JTabbedPane();
  public LinkedList<File> openedFiles = new LinkedList<>();

  public TextEditor(JScrollPane projectPane) {
    this.projectPane = projectPane;
  }

  public void open(File file) {
    if (file.isFile()) {
      doOpenFile(file);
    } else if (file.isDirectory()) {
      doOpenProject(file);
    }
  }

  private void doOpenProject(File directory) {
    DefaultMutableTreeNode root = new DefaultMutableTreeNode(new TreeNodeFileWrapper(directory));
    addChildrenTreeNode(root, directory);

    JTree tree = new JTree(root);
    tree.setFont(new Font(null, Font.PLAIN, 16));
    tree.setRowHeight(24);
    FlatTreeUI treeUi = (FlatTreeUI) tree.getUI();
    treeUi.setCollapsedIcon(new ImageIcon(this.getClass().getResource("/img/triangle-right.png")));
    treeUi.setExpandedIcon(new ImageIcon(this.getClass().getResource("/img/triangle-down.png")));

    DefaultTreeCellRenderer cellRenderer = (DefaultTreeCellRenderer) tree.getCellRenderer();
    cellRenderer.setLeafIcon(new ImageIcon(this.getClass().getResource("/img/file.png")));
    cellRenderer.setOpenIcon(new ImageIcon(this.getClass().getResource("/img/folder.png")));
    cellRenderer.setClosedIcon(new ImageIcon(this.getClass().getResource("/img/folder.png")));
    /*
    tree.addTreeSelectionListener(
        e -> {
          DefaultMutableTreeNode lastPathComponent =
              (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
          File selectedFile = ((TreeNodeFileWrapper) lastPathComponent.getUserObject()).file;
          if (selectedFile.isFile()) {
            doOpenFile(selectedFile);
          }
        });*/

    tree.addMouseListener(
        new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            // 左键双击和右键单击
            TreePath path = tree.getClosestPathForLocation(e.getX(), e.getY());
            DefaultMutableTreeNode lastPathComponent =
                (DefaultMutableTreeNode) path.getLastPathComponent();
            File selectedFile = ((TreeNodeFileWrapper) lastPathComponent.getUserObject()).file;
            if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
              if (selectedFile.isFile()) {
                doOpenFile(selectedFile);
              }
            } else if (SwingUtilities.isRightMouseButton(e) && e.getClickCount() == 1) {
              new FileJPopupMenu(selectedFile).show(e.getComponent(), e.getX(), e.getY());
            }
          }
        });

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

    projectPane.setViewportView(panel);
  }

  private void addChildrenTreeNode(DefaultMutableTreeNode node, File file) {
    if (file.isFile()) {
      return;
    }
    File directory = file;
    File[] files = Optional.ofNullable(directory.listFiles()).orElseGet(() -> new File[0]);
    for (File f : files) {
      DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(new TreeNodeFileWrapper(f));
      node.add(childNode);
      addChildrenTreeNode(childNode, f);
    }
  }

  private void doOpenFile(File file) {
    int indexOfOpenedFile = getIndexOfOpenedFile(file);
    if (indexOfOpenedFile >= 0) {
      textEditorTabbedPane.setSelectedIndex(indexOfOpenedFile);
      return;
    }

    int indexInTabbedPane = openedFiles.size();
    JTextPaneWrapper jTextPane = new JTextPaneWrapper(this, file);

    JScrollPane jScrollPane = new JScrollPane(jTextPane);

    textEditorTabbedPane.add(jScrollPane);
    textEditorTabbedPane.setTabComponentAt(
        indexInTabbedPane, getClosingTitleTab(file.getName(), file));

    openedFiles.add(file);
    textEditorTabbedPane.setSelectedIndex(indexInTabbedPane);

    jTextPane.setText(FileUtil.getString(file, IdeContext.get().getFileEncoding()));
  }

  private JPanel getClosingTitleTab(String tabName, File file) {
    ImageIcon closingBtnIcon = new ImageIcon(this.getClass().getResource("/img/closing.png"));
    JButton closingBtn = new JButton(closingBtnIcon);
    closingBtn.setBorderPainted(false);
    closingBtn.setContentAreaFilled(false);
    closingBtn.addActionListener(new ClosingTitleTabActionListener(this, file));

    JPanel panel = new JPanel();
    panel.setBorder(BorderFactory.createEmptyBorder());
    panel.setOpaque(false);

    panel.add(new JLabel(tabName));
    panel.add(closingBtn);
    return panel;
  }

  public void saveTextToFile(File file) {
    saveTextToFile(getIndexOfOpenedFile(file));
  }

  private void saveTextToFile(int indexOfTabbedPane) {
    JScrollPane jScrollPane = (JScrollPane) textEditorTabbedPane.getComponentAt(indexOfTabbedPane);
    JTextPaneWrapper jTextPane = (JTextPaneWrapper) jScrollPane.getViewport().getView();
    String text = jTextPane.getText();
    FileUtil.write(openedFiles.get(indexOfTabbedPane), text, IdeContext.get().getFileEncoding());
  }

  private int getIndexOfOpenedFile(File file) {
    int indexOfFile = -1;
    int indexOfOpenedFiles = 0;
    for (File f : openedFiles) {
      if (f.equals(file)) {
        indexOfFile = indexOfOpenedFiles;
        break;
      }
      ++indexOfOpenedFiles;
    }
    return indexOfFile;
  }

  public void actionOnWindowClosing() {
    for (int indexOfOpenedFile = 0; indexOfOpenedFile < openedFiles.size(); indexOfOpenedFile++) {
      saveTextToFile(indexOfOpenedFile);
    }
  }

  public void actionOnTabClosing(int indexInTabbedPane) {
    saveTextToFile(indexInTabbedPane);
    textEditorTabbedPane.getComponentAt(indexInTabbedPane);
    textEditorTabbedPane.remove(indexInTabbedPane);
    openedFiles.remove(indexInTabbedPane);
  }

  public void reloadTextPanes() {
    for (int indexOfComponents = 0;
        indexOfComponents < textEditorTabbedPane.getTabCount();
        indexOfComponents++) {
      JScrollPane jScrollPane =
          (JScrollPane) textEditorTabbedPane.getComponentAt(indexOfComponents);
      JTextPaneWrapper jTextPane = (JTextPaneWrapper) jScrollPane.getViewport().getView();
      jTextPane.reload();
    }
  }

  static class ClosingTitleTabActionListener implements ActionListener {
    TextEditor textEditor;
    File file;

    public ClosingTitleTabActionListener(TextEditor textEditor, File file) {
      this.textEditor = textEditor;
      this.file = file;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      textEditor.actionOnTabClosing(textEditor.getIndexOfOpenedFile(file));
    }
  }
}
