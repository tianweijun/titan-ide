package titan.ide.window.mennu;

import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import titan.ide.window.MainWindow;

/**
 * .
 *
 * @author tian wei jun
 */
public class MainWindowMenuBar {
  MainWindow mainWindow;
  JMenuBar menuBar;

  public MainWindowMenuBar(MainWindow mainWindow) {
    this.mainWindow = mainWindow;
    mainWindow.setJMenuBar(initMenu());
  }

  private JMenuBar initMenu() {
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

    // new Project菜单项
    JMenuItem newProjectMenuItem = new JMenuItem("project");
    newProjectMenuItem.setActionCommand("NewProjectMenuItem");
    newProjectMenuItem.addActionListener(new NewProjectMenuItemListener());
    newProjectMenuItem.setIconTextGap(20);
    newMenu.add(newProjectMenuItem);
    fileMenu.add(newMenu);

    // open 菜单项
    JMenuItem openMenuItem = new JMenuItem("<html><u>O</u>pen...</html>");
    openMenuItem.setMnemonic(KeyEvent.VK_O);
    openMenuItem.setActionCommand("OpenMenuItem");
    openMenuItem.addActionListener(new OpenMenuItemListener());
    openMenuItem.setIconTextGap(20);
    fileMenu.add(openMenuItem);

    // settings
    JMenuItem settingsMenuItem = new JMenuItem("Settings...");
    settingsMenuItem.setActionCommand("SettingsMenuItem");
    settingsMenuItem.addActionListener(new SettingsMenuItemListener());
    settingsMenuItem.setIconTextGap(20);
    fileMenu.add(settingsMenuItem);
  }
}
