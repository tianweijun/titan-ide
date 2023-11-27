package titan.ide.window.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
 * .
 *
 * @author tian wei jun
 */
public class TextEditor {
  public JScrollPane projectPane;
  public JTabbedPane textEditorPane = new JTabbedPane();
  public ArrayList<File> files = new ArrayList<>();

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
    DefaultMutableTreeNode root = new DefaultMutableTreeNode(directory);
    addChildrenTreeNode(root, directory);
    JTree tree = new JTree(root); // 左边就是我们的文件树

    tree.addTreeSelectionListener(
        e -> {
          DefaultMutableTreeNode lastPathComponent =
              (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
          File selectedFile = (File) lastPathComponent.getUserObject();
          if (selectedFile.isFile()) {
            doOpenFile(selectedFile);
          }
        });

    tree.addMouseListener(
        new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e)) {
              TreePath path = tree.getPathForLocation(e.getX(), e.getY());
              if (null == path) {
                return;
              }
              DefaultMutableTreeNode lastPathComponent =
                  (DefaultMutableTreeNode) path.getLastPathComponent();
              File selectedFile = (File) lastPathComponent.getUserObject();
              if (selectedFile.isFile()) {
                doOpenFile(selectedFile);
                return;
              }
              if (selectedFile.isDirectory() && !lastPathComponent.isLeaf()) {
                if (tree.isExpanded(path)) {
                  tree.collapsePath(path);
                } else {
                  tree.expandPath(path);
                }
              }
            }
          }
        });
    projectPane.setViewportView(tree);
  }

  private void addChildrenTreeNode(DefaultMutableTreeNode node, File file) {
    if (file.isFile()) {
      return;
    }
    File directory = file;
    File[] files = Optional.ofNullable(directory.listFiles()).orElseGet(() -> new File[0]);
    for (File f : files) {
      DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(f);
      node.add(newNode);
      addChildrenTreeNode(newNode, f);
    }
  }

  private void doOpenFile(File file) {
    int indexOfFile = getFileIndex(file);
    if (indexOfFile >= 0) {
      textEditorPane.setSelectedIndex(indexOfFile);
      return;
    }
    JTextPane jTextPane = new JTextPane();
    jTextPane.setText(FileUtil.getString(file));
    JScrollPane jScrollPane = new JScrollPane(jTextPane);
    textEditorPane.add(jScrollPane);
    addTabClosingTitle(file.getName(), jScrollPane);

    files.add(file);
    textEditorPane.setSelectedIndex(files.size() - 1);
  }

  private void addTabClosingTitle(String tabName, JScrollPane jTextPane) {
    JPanel pane = new JPanel();
    pane.setOpaque(false); // 设置jpanel面板背景透明

    JLabel title = new JLabel(tabName);
    pane.add(title);

    URL resource = this.getClass().getResource("/img/closing.png");
    ImageIcon closingBtnIcon = new ImageIcon(resource);
    JButton closingBtn = new JButton(closingBtnIcon);
    closingBtn.setBorderPainted(false);
    closingBtn.setContentAreaFilled(false);
    pane.add(closingBtn);
    closingBtn.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            int index = textEditorPane.indexOfComponent(jTextPane);
            textEditorPane.remove(index);
            files.remove(index);
          }
        });

    textEditorPane.setTabComponentAt(textEditorPane.indexOfComponent(jTextPane), pane);
  }

  private int getFileIndex(File file) {
    int indexOfFile = -1;
    int index = 0;
    for (File f : files) {
      if (f.equals(file)) {
        indexOfFile = index;
        break;
      }
      ++index;
    }
    return indexOfFile;
  }
}
