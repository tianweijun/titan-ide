package titan.ide.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import titan.ide.services.MainWindowService;
import titan.ide.window.mennu.MenuActionListener;
import titan.ide.window.view.TextEditor;

/**
 * .
 *
 * @author tian wei jun
 */
public class MainWindowViewManager {
  public MainWindowService mainWindowService;

  public MainWindow mainWindow;

  public JMenuBar menuBar;
  public JPanel topToolBar;
  public JPanel jpanelInTopToolBarLeft;
  public JPanel jpanelInTopToolBarRight;
  public JSplitPane contentPane;
  public JSplitPane workspacePane;
  public JScrollPane projectPane;
  public TextEditor textEditor;
  public JScrollPane bottomToolBarScrollPane;
  public JTabbedPane bottomToolBar;
  public JPanel statusBar;
  public JPanel jpanelInStatusBarLeft;
  public JPanel jpanelInStatusBarRight;

  MenuActionListener menuActionListener;

  public MainWindowViewManager(MainWindow mainWindow) {
    this.mainWindow = mainWindow;
    mainWindowService = new MainWindowService();
  }

  public void init() {
    mainWindow.setMinimumSize(new Dimension(900, 800));
    mainWindow.setResizable(true);
    mainWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
    mainWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    mainWindow.addWindowListener(
        new java.awt.event.WindowAdapter() {
          @Override
          public void windowClosing(java.awt.event.WindowEvent evt) {
            mainWindowService.actionOnWindowClosing();
            mainWindow.dispose();
          }
        });
    mainWindow.setLayout(new BorderLayout());

    setLogoIcon();

    mainWindow.setJMenuBar(this.initMenu());

    mainWindow.add(initTopToolBar(), BorderLayout.NORTH);
    mainWindow.add(initContentPane(), BorderLayout.CENTER);
    mainWindow.add(initStatusBar(), BorderLayout.SOUTH);
  }

  private JPanel initStatusBar() {
    statusBar = new JPanel(new BorderLayout());
    statusBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY));
    jpanelInStatusBarLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jpanelInStatusBarRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    statusBar.add(jpanelInStatusBarLeft, BorderLayout.WEST);
    statusBar.add(jpanelInStatusBarRight, BorderLayout.EAST);

    addMsgToLeftStatusBar("All files are up-to-date (moments ago)");
    addMsgToRightStatusBar("UTF-8");
    return statusBar;
  }

  private void addMsgToRightStatusBar(String msg) {
    JLabel msgLabel = new JLabel(msg);
    msgLabel.setForeground(Color.BLUE);
    jpanelInStatusBarRight.add(msgLabel);
  }

  private void addMsgToLeftStatusBar(String msg) {
    JLabel msgLabel = new JLabel(msg);
    jpanelInStatusBarLeft.add(msgLabel);
  }

  private JPanel initTopToolBar() {
    topToolBar = new JPanel(new BorderLayout());
    topToolBar.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.GRAY));
    jpanelInTopToolBarLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jpanelInTopToolBarRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    topToolBar.add(jpanelInTopToolBarLeft, BorderLayout.WEST);
    topToolBar.add(jpanelInTopToolBarRight, BorderLayout.EAST);

    addRunBtnInTopToolBar();
    addDebugBtnInTopToolBar();

    return topToolBar;
  }

  private void addDebugBtnInTopToolBar() {
    URL debugBtnImgResource = this.getClass().getResource("/img/debug.png");
    ImageIcon debugBtnIcon = new ImageIcon(debugBtnImgResource);
    JButton debugBtn = new JButton(debugBtnIcon);
    debugBtn.setBorderPainted(false);
    debugBtn.setContentAreaFilled(true);
    debugBtn.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            addDebugInBottomToolBar();
          }
        });
    jpanelInTopToolBarRight.add(debugBtn);
  }

  private void addRunBtnInTopToolBar() {
    URL runBtnImgResource = this.getClass().getResource("/img/run.png");
    ImageIcon runBtnIcon = new ImageIcon(runBtnImgResource);
    JButton runBtn = new JButton(runBtnIcon);
    runBtn.setBorderPainted(false);
    runBtn.setContentAreaFilled(true);
    runBtn.addActionListener(
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            addRunInBottomToolBar();
          }
        });
    jpanelInTopToolBarRight.add(runBtn);
  }

  private void addDebugInBottomToolBar() {
    JLabel debugLabel = new JLabel("debug");
    bottomToolBar.addTab("debug", debugLabel);
    contentPane.setDividerLocation(
        contentPane.getHeight()
            - contentPane.getDividerSize()
            - bottomToolBarScrollPane.getInsets().top
            - 200);
  }

  private void addRunInBottomToolBar() {
    JLabel runLabel = new JLabel("run");
    bottomToolBar.addTab("run", runLabel);
    contentPane.setDividerLocation(
        contentPane.getHeight()
            - contentPane.getDividerSize()
            - bottomToolBarScrollPane.getInsets().top
            - 200);
  }

  private JSplitPane initContentPane() {
    contentPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    contentPane.setLeftComponent(initWorkspaceContainer());
    contentPane.setRightComponent(initBottomToolBar());
    contentPane.setResizeWeight(1.0);
    return contentPane;
  }

  private JScrollPane initBottomToolBar() {
    bottomToolBarScrollPane = new JScrollPane();
    bottomToolBarScrollPane.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.GRAY));

    bottomToolBar = new JTabbedPane(SwingConstants.TOP);
    bottomToolBar.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);

    bottomToolBarScrollPane.setViewportView(bottomToolBar);
    return bottomToolBarScrollPane;
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
    workspacePane.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
    workspacePane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
    workspacePane.setDividerLocation(340);
    // 横向分割之后，我们需要指定左右两边的组件
    workspacePane.setLeftComponent(initProjectContainer());
    workspacePane.setRightComponent(initTextEditorContainer());
    return workspacePane;
  }

  private Component initProjectContainer() {
    projectPane = new JScrollPane();
    projectPane.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY));
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
