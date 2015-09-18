package irui;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.EditorKit;

public class EditorPaneExample2 extends JFrame {
  public EditorPaneExample2() {
    super("JEditorPane Example 2");

    pane = new JEditorPane();
    pane.setEditable(false); // Read-only

    getContentPane().setLayout(new GridBagLayout());

    GridBagConstraints c = new GridBagConstraints();
    c.gridwidth = 2;
    c.gridheight = 1;
    c.anchor = GridBagConstraints.CENTER;
    c.fill = GridBagConstraints.BOTH;
    c.weightx = 1.0;
    c.weighty = 1.0;
    c.gridx = 0;
    c.gridy = 0;
    c.insets = new Insets(2, 2, 2, 2);
    getContentPane().add(new JScrollPane(pane), c);

    // Build the panel of controls
    JPanel upperPanel = new JPanel();
    upperPanel.setLayout(new BorderLayout());

    // Add a text area with a surrounding border
    textArea = new JTextArea(5, 30);
    JPanel textPanel = new JPanel();
    textPanel.setLayout(new BorderLayout());
    textPanel.setBorder(BorderFactory.createTitledBorder("Source Text"));
    textPanel.add(new JScrollPane(textArea));
    upperPanel.add(textPanel, "Center"); // Add the text panel

    // Build a subpanel with two radio buttons to
    // select document type and a button to install
    // new text.
    Box controlPanel = new Box(BoxLayout.Y_AXIS);

    // Add the radio buttons
    ButtonGroup group = new ButtonGroup();
    plainButton = new JRadioButton("Plain Text");
    htmlButton = new JRadioButton("HTML");
    group.add(plainButton);
    group.add(htmlButton);

    controlPanel.add(plainButton);
    controlPanel.add(htmlButton);

    // Add a button to install the text
    JButton installButton = new JButton("Install text");
    controlPanel.add(installButton);

    // Add glue to pad out extra space
    controlPanel.add(Box.createVerticalGlue());

    // Add the controls to the upper panel
    // and the upper panel to the content pane
    upperPanel.add(controlPanel, "East");

    c.gridy = 1;
    c.weighty = 0.0; // No vertical expansion
    getContentPane().add(upperPanel, c);

    // Add labels showing the current editor kit and document
    c.gridy = 2;
    c.gridwidth = 1;
    c.weightx = 0.0;
    c.fill = GridBagConstraints.NONE;
    getContentPane().add(new JLabel("Document: ", JLabel.RIGHT), c);
    c.gridy = 3;
    getContentPane().add(new JLabel("Editor Kit:", JLabel.RIGHT), c);

    docLabel = new JLabel("", JLabel.LEFT);
    kitLabel = new JLabel("", JLabel.LEFT);
    c.gridy = 2;
    c.gridx = 1;
    c.weightx = 1.0;
    c.fill = GridBagConstraints.HORIZONTAL;
    getContentPane().add(docLabel, c);
    c.gridy = 3;
    getContentPane().add(kitLabel, c);

    // Add a listener to the button
    installButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        // Get the text and install it in the JEditorPane
        SwingUtilities.invokeLater(new Runnable() {
          public void run() {
            pane.setText(textArea.getText());
          }
        });
      }
    });

    // Add listeners to the radio buttons
    ActionListener radioButtonListener = new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        JRadioButton b = (JRadioButton) evt.getSource();
        String type = ((b == plainButton) ? "text/plain" : "text/html");
        final EditorKit kit = pane.getEditorKitForContentType(type);

        SwingUtilities.invokeLater(new Runnable() {
          public void run() {
            pane.setEditorKit(kit);
          }
        });
      }
    };

    // Listen to the properties of the editor pane
    pane.addPropertyChangeListener(new PropertyChangeListener() {
      public void propertyChange(PropertyChangeEvent evt) {
        String prop = evt.getPropertyName();
        if (prop.equals("document")) {
          docLabel.setText(evt.getNewValue().getClass().getName());
        } else if (prop.equals("editorKit")) {
          kitLabel.setText(evt.getNewValue().getClass().getName());
        }
      }
    });
    pane.addHyperlinkListener(new HyperlinkListener() {
        public void hyperlinkUpdate(HyperlinkEvent e) {
          if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            try {
            	pane.setToolTipText(e.getURL().toString());
              String command = "explorer.exe "//如果是记事本打开,可用 notepad.exe
                  + e.getURL().toString();
            //  Runtime.getRuntime().exec(command);
            }
            catch (Exception ex) {
              ex.printStackTrace();
            }
          }
        }
      });

    plainButton.addActionListener(radioButtonListener);
    htmlButton.addActionListener(radioButtonListener);

    // Finally, start off by selecting plain text
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        plainButton.setSelected(true);
        docLabel.setText(pane.getDocument().getClass().getName());
        kitLabel.setText(pane.getEditorKit().getClass().getName());
      }
    });
  }

  public static void main(String[] args) {
    try {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
    } catch (Exception evt) {}
  
    JFrame f = new EditorPaneExample2();
    f.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent evt) {
        System.exit(0);
      }
    });
    f.setSize(500, 400);
    f.setVisible(true);
  }

  private JEditorPane pane;

  private JTextArea textArea;

  private JRadioButton plainButton;

  private JRadioButton htmlButton;

  private JLabel docLabel;

  private JLabel kitLabel;
}
