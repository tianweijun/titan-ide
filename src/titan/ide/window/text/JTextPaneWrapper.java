package titan.ide.window.text;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.AbstractAction;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Keymap;
import javax.swing.text.SimpleAttributeSet;
import titan.ide.config.IdeConfig;
import titan.ide.context.IdeContext;
import titan.ide.exception.IdeRuntimeException;

/**
 * .
 *
 * @author tian wei jun
 */
public class JTextPaneWrapper extends JTextPane {
  public File file;

  public JTextPaneWrapper(TextEditor textEditor, File file) {
    super();
    this.file = file;
    IdeConfig ideConfig = IdeContext.get().ideConfig;
    setFont(new Font(ideConfig.fontNameOfTextEditor, Font.PLAIN, ideConfig.fontSizeOfTextEditor));
    setBackground(new Color(0xC7EDCC));

    setBorder(new LineNumberBorder());

    addMouseListener(
        new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            // System.out.println("x:" + x + ",y:" + y);
          }
        });
    Keymap keymap = getKeymap();
    keymap.addActionForKeyStroke(
        KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK),
        new AbstractAction() {
          @Override
          public void actionPerformed(ActionEvent e) {
            textEditor.saveTextToFile(((JTextPaneWrapper) e.getSource()).file);
          }
        });
  }

  public void insertString(String text, SimpleAttributeSet attributeSet) {
    Document document = getDocument();
    try {
      document.insertString(document.getLength(), text, attributeSet);
    } catch (BadLocationException e) {
      throw new IdeRuntimeException(e);
    }
  }
}
