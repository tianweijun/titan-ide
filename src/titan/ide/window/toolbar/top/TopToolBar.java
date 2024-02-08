package titan.ide.window.toolbar.top;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import titan.ide.window.toolbar.bottom.BottomToolBar;
import titan.ide.window.toolbar.bottom.BottomToolBar.BottomToolBarItemType;

/**
 * .
 *
 * @author tian wei jun
 */
public class TopToolBar extends JPanel {
  BottomToolBar bottomToolBar;
  public JPanel jpanelInTopToolBarLeft;
  public JPanel jpanelInTopToolBarRight;

  public TopToolBar(BottomToolBar bottomToolBar) {
    super();
    this.bottomToolBar = bottomToolBar;

    setLayout(new BorderLayout());

    setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.GRAY));
    jpanelInTopToolBarLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
    jpanelInTopToolBarRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    add(jpanelInTopToolBarLeft, BorderLayout.WEST);
    add(jpanelInTopToolBarRight, BorderLayout.EAST);

    addRunBtnInTopToolBar();
    addDebugBtnInTopToolBar();
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
            bottomToolBar.openBarItem(BottomToolBarItemType.DEBUG);
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
            bottomToolBar.openBarItem(BottomToolBarItemType.RUN);
          }
        });
    jpanelInTopToolBarRight.add(runBtn);
  }
}
