package titan.ide.window.mennu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import titan.ide.context.IdeContext;
import titan.ide.window.MainWindow;

/**
 * .
 *
 * @author tian wei jun
 */
public class OpenMenuItemListener implements ActionListener {

  @Override
  public void actionPerformed(ActionEvent e) {
    JFileChooser jFileChooser = new JFileChooser();
    jFileChooser.setMultiSelectionEnabled(false);
    jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    // 设置默认打开的目录
    jFileChooser.setCurrentDirectory(new File("D:\\github-pro\\titan\\test"));
    // 文件选择对话框
    IdeContext ideContext = IdeContext.get();
    MainWindow mainWindow = ideContext.mainWindow;
    int clickBtn = jFileChooser.showOpenDialog(mainWindow);
    if (clickBtn == JFileChooser.APPROVE_OPTION) {
      // 说明选中了某个文件，点击了打开按钮
      File selectedFile = jFileChooser.getSelectedFile();
      mainWindow.open(selectedFile);
    }
  }
}
