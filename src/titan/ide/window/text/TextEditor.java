package titan.ide.window.text;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;
import java.util.LinkedList;
import java.util.Optional;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
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

    tree.addTreeSelectionListener(
        e -> {
          DefaultMutableTreeNode lastPathComponent =
              (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
          File selectedFile = ((TreeNodeFileWrapper) lastPathComponent.getUserObject()).file;
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
              File selectedFile = ((TreeNodeFileWrapper) lastPathComponent.getUserObject()).file;
              if (selectedFile.isFile()) {
                doOpenFile(selectedFile);
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
      DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(new TreeNodeFileWrapper(f));
      node.add(newNode);
      addChildrenTreeNode(newNode, f);
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

    SimpleAttributeSet attributeSet = new SimpleAttributeSet();
    StyleConstants.setBold(attributeSet, true);
    jTextPane.insertString(FileUtil.getString(file), attributeSet);
  }

  private JPanel getClosingTitleTab(String tabName, File file) {
    JLabel title = new JLabel(tabName);

    URL resource = this.getClass().getResource("/img/closing.png");
    ImageIcon closingBtnIcon = new ImageIcon(resource);
    JButton closingBtn = new JButton(closingBtnIcon);
    closingBtn.setBorderPainted(false);
    closingBtn.setContentAreaFilled(false);
    closingBtn.addActionListener(new ClosingTitleTabActionListener(this, file));

    JPanel pane = new JPanel();
    pane.add(title);
    pane.add(closingBtn);
    return pane;
  }

  public void saveTextToFile(File file) {
    saveTextToFile(getIndexOfOpenedFile(file));
  }

  private void saveTextToFile(int indexOfTabbedPane) {
    JScrollPane jScrollPane = (JScrollPane) textEditorTabbedPane.getComponentAt(indexOfTabbedPane);
    JTextPaneWrapper jTextPane = (JTextPaneWrapper) jScrollPane.getViewport().getView();
    String text = jTextPane.getText();
    FileUtil.write(openedFiles.get(indexOfTabbedPane), text);
  }

  private int getIndexOfOpenedFile(File file) {
    int indexOfFile = -1;
    int index = 0;
    for (File f : openedFiles) {
      if (f.equals(file)) {
        indexOfFile = index;
        break;
      }
      ++index;
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
