package titan.ide.window.toolbar.bottom;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
 * .
 *
 * @author tian wei jun
 */
public class BottomToolBar extends JPanel {
  JSplitPane contentVerticalSplitPane;
  JPanel bottomToolBarItemContenPanel;
  JPanel emptySpaceFill = new JPanel();
  List<BottomToolBarItem> barItems = new LinkedList<>();
  HashMap<BottomToolBarItemType, JComponent> contentOfBarItems =
      new HashMap<>(BottomToolBarItemType.values().length);
  BottomToolBarItem openedBarItem = null;

  public BottomToolBar(JSplitPane contentVerticalSplitPane, JPanel bottomToolBarItemContenPanel) {
    this.contentVerticalSplitPane = contentVerticalSplitPane;
    this.bottomToolBarItemContenPanel = bottomToolBarItemContenPanel;

    emptySpaceFill.setOpaque(false);

    setLayout(new GridBagLayout());

    addEmptyBarItem();
    ceateBarItem(BottomToolBarItemType.RUN);
  }

  private void mouseClickedBarItem(BottomToolBarItem barItem) {
    if (barItem == openedBarItem) {
      closeBarItem(barItem);
    } else {
      openBarItem(barItem);
    }
  }

  private void closeBarItem(BottomToolBarItem barItem) {
    Component bottomComponent = contentVerticalSplitPane.getBottomComponent();
    if (null != bottomComponent) {
      openedBarItem = null;
      contentVerticalSplitPane.setDividerSize(0);
      contentVerticalSplitPane.remove(bottomComponent);

      Container centerPane = contentVerticalSplitPane.getParent();
      centerPane.revalidate();
      centerPane.repaint();
    }
  }

  public void openBarItem(BottomToolBarItemType itemType) {
    BottomToolBarItem barItem = getBarItemByType(itemType);
    if (null == barItem) {
      barItem = ceateBarItem(itemType);
    }
    openBarItem(barItem);
  }

  private BottomToolBarItem getBarItemByType(BottomToolBarItemType itemType) {
    BottomToolBarItem barItem = null;
    for (BottomToolBarItem item : barItems) {
      if (item.itemType == itemType) {
        barItem = item;
        break;
      }
    }
    return barItem;
  }

  private void openBarItem(BottomToolBarItem barItem) {
    JComponent contentOfBarItem = contentOfBarItems.get(barItem.itemType);
    if (null == contentOfBarItem) {
      contentOfBarItem = ceateContentBarItem(barItem.itemType);
    }

    contentVerticalSplitPane.setDividerSize(8);
    contentVerticalSplitPane.setDividerLocation(
        contentVerticalSplitPane.getHeight() - contentVerticalSplitPane.getDividerSize() - 200);
    contentVerticalSplitPane.setBottomComponent(contentOfBarItem);

    // set activated
    activated(barItem);

    Container centerPane = contentVerticalSplitPane.getParent();
    centerPane.revalidate();
    centerPane.repaint();

    openedBarItem = barItem;
  }

  private JComponent ceateContentBarItem(BottomToolBarItemType itemType) {
    JComponent contentOfBarItem = null;
    switch (itemType) {
      case RUN:
        contentOfBarItem = new RunJComponent();
        break;
      case DEBUG:
        contentOfBarItem = new DebugJComponent();
        break;
      default:
    }
    contentOfBarItems.put(itemType, contentOfBarItem);
    return contentOfBarItem;
  }

  private void activated(BottomToolBarItem barItem) {
    for (BottomToolBarItem bottomToolBarItem : barItems) {
      bottomToolBarItem.normal();
    }
    barItem.activated();
  }

  public BottomToolBarItem ceateBarItem(BottomToolBarItemType itemType) {
    JPanel itemWrapper = new JPanel();

    itemWrapper.setBackground(new Color(0xEEEEEE));
    BottomToolBarItem item = new BottomToolBarItem(itemType);
    item.setBorder(BorderFactory.createEmptyBorder(0, 6, 0, 6));
    item.addMouseListener(
        new MouseAdapter() {
          @Override
          public void mouseEntered(MouseEvent e) {
            BottomToolBarItem source = (BottomToolBarItem) e.getSource();
            if (source != openedBarItem) {
              source.hover();
            }
          }

          @Override
          public void mouseExited(MouseEvent e) {
            BottomToolBarItem source = (BottomToolBarItem) e.getSource();
            if (source != openedBarItem) {
              source.normal();
            }
          }

          @Override
          public void mouseClicked(MouseEvent e) {
            mouseClickedBarItem(((BottomToolBarItem) e.getSource()));
          }
        });
    itemWrapper.add(item);
    barItems.add(item);

    int componentCount = getComponentCount();
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = componentCount - 1;
    gbc.gridy = 0;

    remove(componentCount - 1);
    add(itemWrapper, gbc);
    addEmptyBarItem();

    return item;
  }

  private void addEmptyBarItem() {
    GridBagConstraints gbc = new GridBagConstraints();

    gbc.gridx = getComponentCount();
    gbc.gridy = 0;
    gbc.weightx = 1.0;
    gbc.weighty = 1.0;
    gbc.fill = GridBagConstraints.BOTH;
    add(emptySpaceFill, gbc);
  }

  public static class BottomToolBarItem extends JLabel {
    public BottomToolBarItemType itemType;

    public BottomToolBarItem(BottomToolBarItemType itemType) {
      super(itemType.getName());
      this.itemType = itemType;
    }

    public void activated() {
      getParent().setBackground(new Color(0xBFBFBF));
    }

    public void hover() {
      getParent().setBackground(new Color(0xDBDBDB));
    }

    public void normal() {
      getParent().setBackground(new Color(0xEEEEEE));
    }
  }

  public static enum BottomToolBarItemType {
    RUN("run"),
    DEBUG("debug");

    private String name;

    BottomToolBarItemType(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }
  }
}
