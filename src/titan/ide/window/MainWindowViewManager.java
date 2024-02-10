package titan.ide.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.io.File;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import titan.ide.services.MainWindowService;
import titan.ide.window.mennu.MainWindowMenuBar;
import titan.ide.window.statusbar.StatusBar;
import titan.ide.window.text.TextEditor;
import titan.ide.window.toolbar.bottom.BottomToolBar;
import titan.ide.window.toolbar.top.TopToolBar;

/**
 * .
 *
 * @author tian wei jun
 */
public class MainWindowViewManager {
  public MainWindowService mainWindowService;

  public MainWindow mainWindow;

  public MainWindowMenuBar menuBar;
  public JPanel topToolBar;
  public JSplitPane contentVerticalSplitPane;
  public JSplitPane workspacePane;
  public JPanel bottomToolBarItemContenPanel;
  public JScrollPane projectPane;
  public TextEditor textEditor;
  public BottomToolBar bottomToolBar;
  public JPanel statusBar;

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
            mainWindow.actionOnWindowClosing();
            mainWindowService.actionOnWindowClosing();
            mainWindow.dispose();
          }
        });
    mainWindow.setLayout(new BorderLayout());

    setLogoIcon();

    setMenu();

    mainWindow.add(initCenterPane(), BorderLayout.CENTER);
    // initCenterPane()在initTopToolBar()之前，因为TopToolBar依赖CenterPane中的bottomToolBar.
    mainWindow.add(initTopToolBar(), BorderLayout.NORTH);
    mainWindow.add(initStatusBar(), BorderLayout.SOUTH);
  }

  private void setMenu() {
    menuBar = new MainWindowMenuBar(mainWindow);
  }

  private JPanel initStatusBar() {
    statusBar = new StatusBar();
    return statusBar;
  }

  private JPanel initTopToolBar() {
    topToolBar = new TopToolBar(bottomToolBar);
    return topToolBar;
  }

  private JPanel initCenterPane() {
    bottomToolBarItemContenPanel = new JPanel();
    bottomToolBarItemContenPanel.setOpaque(false);

    contentVerticalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    contentVerticalSplitPane.setTopComponent(initWorkspaceContainer());
    // contentVerticalSplitPane.setBottomComponent(bottomToolBarItemContenPanel);
    contentVerticalSplitPane.setResizeWeight(1.0);
    contentVerticalSplitPane.setDividerSize(0);
    contentVerticalSplitPane.setOneTouchExpandable(false);

    JPanel centerPanel = new JPanel(new BorderLayout());
    centerPanel.add(initBottomToolBar(), BorderLayout.SOUTH);
    centerPanel.add(contentVerticalSplitPane, BorderLayout.CENTER);

    return centerPanel;
  }

  private JComponent initBottomToolBar() {
    bottomToolBar = new BottomToolBar(contentVerticalSplitPane, bottomToolBarItemContenPanel);
    return bottomToolBar;
  }

  private void setLogoIcon() {
    URL resource = this.getClass().getResource("/img/titan-logo.png");
    ImageIcon logoImageIcon = new ImageIcon(resource);
    mainWindow.setIconImage(logoImageIcon.getImage());
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
    return textEditor.textEditorTabbedPane;
  }

  public void open(File file) {
    textEditor.open(file);
  }

  public void actionOnWindowClosing() {
    textEditor.actionOnWindowClosing();
  }

  public void updateProjectContext(File file) {
    mainWindowService.updateProjectContext(file);
  }
}
