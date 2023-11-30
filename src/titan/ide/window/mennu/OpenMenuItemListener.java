package titan.ide.window.mennu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import titan.ide.config.IdeConfig;
import titan.ide.context.IdeContext;
import titan.ide.exception.IdeRuntimeException;
import titan.ide.utli.StringUtils;
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
    jFileChooser.setCurrentDirectory(getLastOpenedFileDirectory());
    // 文件选择对话框
    IdeContext ideContext = IdeContext.get();
    MainWindow mainWindow = ideContext.mainWindow;
    int clickBtn = jFileChooser.showOpenDialog(mainWindow);
    if (clickBtn == JFileChooser.APPROVE_OPTION) {
      // 说明选中了某个文件，点击了打开按钮
      File selectedFile = jFileChooser.getSelectedFile();
      // 记录到最近打开的文件
      recordLastOpenedFileDirectory(selectedFile);
      // 打开文件或项目
      mainWindow.open(selectedFile);
    }
  }

  private void recordLastOpenedFileDirectory(File lastOpenedFile) {
    String strLastOpenedFileDirectory = "";
    File lastOpenedFileDirectory =
        lastOpenedFile.isDirectory() ? lastOpenedFile : lastOpenedFile.getParentFile();
    try {
      strLastOpenedFileDirectory = lastOpenedFileDirectory.getCanonicalPath();
      // windows file.getCanonicalPath() \\不转义
      if (File.separatorChar == '\\') {
        strLastOpenedFileDirectory = strLastOpenedFileDirectory.replace("\\", "\\\\");
      }
    } catch (IOException e) {
      throw new IdeRuntimeException(e);
    }
    IdeConfig ideConfig = IdeContext.get().ideConfig;
    ideConfig.lastOpenedFileDirectory = strLastOpenedFileDirectory;
  }

  private File getLastOpenedFileDirectory() {
    IdeConfig ideConfig = IdeContext.get().ideConfig;
    if (StringUtils.isNotBlank(ideConfig.lastOpenedFileDirectory)) {
      String filePath = ideConfig.lastOpenedFileDirectory;
      if (File.separatorChar == '\\') {
        filePath = filePath.replace("\\\\", "\\");
      }
      File lastOpenedFileDirectory = new File(filePath);
      if (lastOpenedFileDirectory.exists()) {
        return lastOpenedFileDirectory;
      }
    }
    String userHomeFilePath = System.getProperty("user.home");
    return new File(userHomeFilePath);
  }
}
