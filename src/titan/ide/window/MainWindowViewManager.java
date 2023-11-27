package titan.ide.window;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;
import titan.ide.window.mennu.MenuActionListener;
import titan.ide.window.view.TextEditor;

/**
 * .
 *
 * @author tian wei jun
 */
public class MainWindowViewManager {
  MainWindow mainWindow;

  JMenuBar menuBar;
  JSplitPane workspacePane;
  JScrollPane projectPane;
  TextEditor textEditor;

  MenuActionListener menuActionListener;

  public MainWindowViewManager(MainWindow mainWindow) {
    this.mainWindow = mainWindow;
  }

  public void init() {
    mainWindow.setLayout(new BorderLayout());

    setLogoIcon();

    mainWindow.setJMenuBar(this.initMenu());

    mainWindow.setMinimumSize(new Dimension(900, 800));
    mainWindow.setResizable(true);
    mainWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
    mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    mainWindow.add(this.initWorkspaceContainer(), BorderLayout.CENTER);
  }

  private void setLogoIcon() {
    URL resource = this.getClass().getResource("/img/titan-logo.png");
    ImageIcon logoImageIcon = new ImageIcon(resource);
    mainWindow.setIconImage(logoImageIcon.getImage());
  }

  private JMenuBar initMenu() {
    menuActionListener = new MenuActionListener();
    menuBar = new JMenuBar();
    this.initFileMenu();
    return menuBar;
  }

  private void initFileMenu() {
    // File菜单
    JMenu fileMenu = new JMenu("<html><u>F</u>ile</html>");
    fileMenu.setMnemonic(KeyEvent.VK_F);
    menuBar.add(fileMenu);

    // new菜单项
    JMenu newMenu = new JMenu("new");
    newMenu.setIconTextGap(20);
    fileMenu.add(newMenu);

    // new Project菜单项
    JMenuItem newProjectMenuItem = new JMenuItem("project");
    newProjectMenuItem.setActionCommand("NewProjectMenuItem");
    newProjectMenuItem.addActionListener(menuActionListener);
    newProjectMenuItem.setIconTextGap(20);
    newMenu.add(newProjectMenuItem);

    // open 菜单项
    JMenuItem openMenuItem = new JMenuItem("<html><u>O</u>pen...</html>");
    openMenuItem.setMnemonic(KeyEvent.VK_O);
    openMenuItem.setActionCommand("OpenMenuItem");
    openMenuItem.addActionListener(menuActionListener);
    openMenuItem.setIconTextGap(20);
    fileMenu.add(openMenuItem);
  }

  private JSplitPane initWorkspaceContainer() {
    workspacePane = new JSplitPane();
    workspacePane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
    workspacePane.setDividerLocation(340);
    // 横向分割之后，我们需要指定左右两边的组件
    workspacePane.setLeftComponent(initProjectContainer());
    workspacePane.setRightComponent(initTextEditorContainer());
    return workspacePane;
  }

  private Component initProjectContainer() {
    projectPane = new JScrollPane();
    return projectPane;
  }

  private JTabbedPane initTextEditorContainer() {
    textEditor = new TextEditor(projectPane);
    return textEditor.textEditorPane;
  }

  public void open(File file) {
    textEditor.open(file);
  }
}
